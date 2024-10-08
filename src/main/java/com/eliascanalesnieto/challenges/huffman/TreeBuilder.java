package com.eliascanalesnieto.challenges.huffman;

import com.eliascanalesnieto.challenges.huffman.model.Branch;
import com.eliascanalesnieto.challenges.huffman.model.Leaf;
import com.eliascanalesnieto.challenges.huffman.model.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TreeBuilder {

    public static Node build(final Map<Character, Long> count) {
        final List<Branch> list = count.entrySet().stream()
                .sorted(compare())
                .map(entry -> new Leaf(entry.getKey(), entry.getValue()))
                .collect(Collectors.toCollection(ArrayList::new));
        if(list.size() < 2) {
            return null;
        }

        while(list.size() > 1) {
            final Branch branch1 = list.removeFirst();
            final Branch branch2 = list.removeFirst();

            final Node node = new Node(branch1, branch2);
            list.add(getIndexByCount(list, node), node);
        }

        return (Node) list.getFirst();
    }

    private static Comparator<Map.Entry<Character, Long>> compare() {
        return (entry1, entry2) -> {
            final int longComparation = Long.compare(entry1.getValue(), entry2.getValue());
            if(longComparation == 0) {
                return Character.compare(entry1.getKey(), entry2.getKey());
            }
            return longComparation;
        };
    }

    private static int getIndexByCount(final List<Branch> list, final Node node) {
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).count() >= node.count()) {
                return i;
            }
        }

        return list.size();
    }
}
