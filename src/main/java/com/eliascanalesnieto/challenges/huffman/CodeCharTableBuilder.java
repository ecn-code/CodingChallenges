package com.eliascanalesnieto.challenges.huffman;

import com.eliascanalesnieto.challenges.huffman.model.Branch;
import com.eliascanalesnieto.challenges.huffman.model.Leaf;
import com.eliascanalesnieto.challenges.huffman.model.Node;
import com.eliascanalesnieto.challenges.huffman.model.RowPrefixChar;

import java.util.HashMap;
import java.util.Map;

public class CodeCharTableBuilder {

    public static Map<String, RowPrefixChar> build(final Node root) throws Exception {
        final Map<String, RowPrefixChar> table = new HashMap<>();
        fill(root, "", table);
        return table;
    }

    private static void fill(final Branch branch, final String code, final Map<String, RowPrefixChar> table) throws Exception {
        if(branch == null) {
            return;
        }

        switch (branch) {
            case Node node -> fillNode(node, code, table);
            case Leaf leaf -> fillLeaf(leaf, code, table);
            default -> throw new Exception("Branch error");
        }
    }

    private static void fillLeaf(final Leaf leaf, final String code, final Map<String, RowPrefixChar> table) {
        table.put(code, new RowPrefixChar(leaf.count(), leaf.character()));
    }

    private static void fillNode(final Node node, final String code, final Map<String, RowPrefixChar> table) throws Exception {
        fill(node.left(), STR."\{code}0", table);
        fill(node.right(), STR."\{code}1", table);
    }
}
