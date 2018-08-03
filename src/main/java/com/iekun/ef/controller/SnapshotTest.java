package com.iekun.ef.controller;

/**
 * Created by java1 on 2018/2/11 0011.
 */
public class SnapshotTest {
    public static void main(String[] args) {
        String a = "hello";
        String b =  new String("hello");
        String c =  new String("hello");
        String d = b.intern();

        System.out.println(a==b);
        System.out.println(b==c);
        System.out.println(b==d);
        System.out.println(a==d);
    }
}
