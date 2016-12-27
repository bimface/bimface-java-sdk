package com.bimface.sdk.bean.response;

import java.io.Serializable;
import java.util.List;

/**
 * 单文件构件类型树的返回参数
 *
 * @author bimface, 2016-11-01.
 */
public class CategoryBean implements Serializable {

    private static final long serialVersionUID = 2363620081297550684L;
    private String            categoryId;                             // 分类id
    private String            categoryName;                           // 分类名称
    private List<Family>      families;                               // 族列表

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Family> getFamilies() {
        return families;
    }

    public void setFamilies(List<Family> families) {
        this.families = families;
    }

    @Override
    public String toString() {
        return "Category [id=" + categoryId + ", name=" + categoryName + ", families=" + families + "]";
    }
}
