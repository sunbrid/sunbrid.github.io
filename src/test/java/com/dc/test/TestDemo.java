package com.dc.test;

/**
 * @author Enzo
 * @Description TODO
 * @date 2018-11-19 11:34
 */
public class TestDemo {
    public static void main(String[] args)throws Exception{
        TestPO po=new TestPO();
        po.setDept_id(266);
        po.setRole_id(266);
        po.setUser_id(266);
        TestPO po1=new TestPO();
        po1.setDept_id(266);
        po1.setRole_id(266);
        po1.setUser_id(266);
        System.out.println(po.getDept_id()==po.getDept_id());
        System.out.println(po.getRole_id()==po.getRole_id());
        System.out.println(po.getUser_id()==po.getUser_id());
    }
}
