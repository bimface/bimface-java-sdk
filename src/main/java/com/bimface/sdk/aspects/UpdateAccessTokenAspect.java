package com.bimface.sdk.aspects;

import com.bimface.exception.BimfaceException;
import com.bimface.sdk.service.AccessTokenService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;

@Aspect
public class UpdateAccessTokenAspect {
    private Logger logger = LoggerFactory.getLogger(UpdateAccessTokenAspect.class);

    @Pointcut("execution(* *(..)) && ( within(com.bimface.sdk.service..*) && !within(com.bimface.sdk.service.AccessTokenService))")
    void serviceMethodPointCut(ProceedingJoinPoint joinPoint) {
    }

    @Around("serviceMethodPointCut(joinPoint)")
    public Object updateTokenOnException(ProceedingJoinPoint joinPoint) throws BimfaceException {
        Object object = null;
        try {
            object = joinPoint.proceed(joinPoint.getArgs());
        } catch (BimfaceException e) {
            logger.warn("request bimface error, error message: {}", e.getMessage());
            if (e.getMessage().contains("invalid access token") || e.getMessage().contains("token was not recognized")) {
                Object service = joinPoint.getThis();
                Field[] fields = service.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.getType().isAssignableFrom(AccessTokenService.class)) {
                        field.setAccessible(true);
                        try {
                            AccessTokenService tokenService = (AccessTokenService) field.get(service);
                            String oldToken = tokenService.getAccessToken();
                            logger.debug("old token: {}", oldToken);
                            tokenService.updateAccessTokenBean();
                            String newToken = tokenService.getAccessToken();
                            logger.debug("new token: {}", newToken);
                            Object[] args = joinPoint.getArgs();
                            for (int i = 0; i < args.length; i++) {
                                if (args[i] instanceof String && oldToken.equals(args[i])) {
                                    args[i] = newToken;
                                    break;
                                }
                            }
                            logger.debug("retry args: {}", Arrays.toString(args));
                            object = joinPoint.proceed(args);
                        } catch (IllegalAccessException e1) {
                            e1.printStackTrace();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        } finally {
                            field.setAccessible(false);
                        }
                        break;
                    }
                }
            } else
                throw e;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return object;
    }
}
