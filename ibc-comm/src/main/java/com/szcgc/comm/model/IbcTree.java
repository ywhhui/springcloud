package com.szcgc.comm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author liaohong
 * @create 2022/9/30 12:32
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IbcTree {

    public String value;
    public String label;
    public List<IbcTree> children;

    private IbcTree() {
    }

//    public IbcTree(String value, String label) {
//        this.value = value;
//        this.label = label;
//    }
//
//    public IbcTree(String value, String label, List<IbcTree> children) {
//        this.value = value;
//        this.label = label;
//        this.children = children;
//    }

    public static IbcTree of(int value, String label) {
        return of(String.valueOf(value), label);
    }

    public static IbcTree of(String value, String label) {
        IbcTree tree = new IbcTree();
        tree.value = value;
        tree.label = label;
        return tree;
    }

    public static IbcTree of(int value, String label, List<IbcTree> children) {
        return of(String.valueOf(value), label, children);
    }

    public static IbcTree of(String value, String label, List<IbcTree> children) {
        IbcTree tree = new IbcTree();
        tree.value = value;
        tree.label = label;
        tree.children = children;
        return tree;
    }

    public static List<IbcTree> of(Map<String, String> map) {
        List<IbcTree> trees = new ArrayList<>(map.size());
        //map.entrySet().stream().forEach(item -> trees.add(IbcTree.of(item.getKey(), item.getValue())));
        //有些map是有序的，所以需要这样子遍历
        for (String key : map.keySet()) {
            trees.add(IbcTree.of(key, map.get(key)));
        }
        return trees;
    }
}
