package com.auto.tool.common;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.internal.rules.FlatModelRules;

/**
 * Created by Administrator on 2018/7/21.
 */
public class MyRules extends FlatModelRules {
    /**
     * Instantiates a new flat model rules.
     *
     * @param introspectedTable the introspected table
     */
    public MyRules(IntrospectedTable introspectedTable) {
        super(introspectedTable);
    }
    @Override
    public boolean generatePrimaryKeyClass() {
        return false;
    }
    @Override
    public boolean generateRecordWithBLOBsClass(){
        return false;
    }
    @Override
    public  boolean   generateDeleteByPrimaryKey(){
        return false;
    }
}
