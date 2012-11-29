package com.harrycodeman.huffman;

import com.harrycodeman.ICompressByteStream;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class PrefixCodesTableForCompress {
    protected static PrefixCodesTableForCompress getPrefixTableForMap(Map<Integer, HuffmanedByte> mapOfBytes) {
        PrefixCodesTableForCompress table = new PrefixCodesTableForCompress();
        table.mapOfBytes = mapOfBytes;
        table.buildHuffmanTree();
        return table;
    }

    public static PrefixCodesTableForCompress buildPrefixTable(ICompressByteStream stream) {
        PrefixCodesTableForCompress table = new PrefixCodesTableForCompress();
        table.countBytes(stream);
        table.buildHuffmanTree();
        return table;
    }

    private void countBytes(ICompressByteStream stream) {
        for (int b : stream) {
            getHuffmanedByteFor(b).incrementReps();
        }
    }

    private HuffmanedByte getHuffmanedByteFor(int b) {
        if (mapOfBytes.containsKey(b)) {
            return mapOfBytes.get(b);
        }
        else {
            HuffmanedByte h = new HuffmanedByte(b);
            mapOfBytes.put(b, h);
            return h;
        }
    }

    private void buildHuffmanTree() {
        TreeSet<ICountedObject> huffmanedBytes = new TreeSet<ICountedObject>(mapOfBytes.values());
        while (huffmanedBytes.size() > 1) {
            huffmanedBytes.add(new HuffmanTreeNode(popFirstAsHuffmanTreeNode(huffmanedBytes),
                    popFirstAsHuffmanTreeNode(huffmanedBytes)));
        }
        root = convertToHuffmanTreeNode(huffmanedBytes.first());
    }

    private static HuffmanTreeNode popFirstAsHuffmanTreeNode(SortedSet<ICountedObject> huffmanedBytes) {
        ICountedObject result = huffmanedBytes.first();
        huffmanedBytes.remove(result);
        return convertToHuffmanTreeNode(result);
    }

    private static HuffmanTreeNode convertToHuffmanTreeNode(ICountedObject value) {
        if (value.getClass() == HuffmanedByte.class) {
            return new HuffmanTreeNode((HuffmanedByte)value);
        }
        return (HuffmanTreeNode)value;
    }

    private Map<Integer, HuffmanedByte> mapOfBytes = new HashMap<Integer, HuffmanedByte>();
    private HuffmanTreeNode root;

    protected PrefixCodesTableForCompress() {
    }

    public int size() {
        return mapOfBytes.size();
    }

    public int getRepsForByte(int b) {
        return mapOfBytes.get(b).getReps();
    }

    public HuffmanTreeNode getRoot() {
        return root;
    }

//    public BitSet compressByte(int b) {
//        // TODO: implement and use!!!
//        return null;
//    }
}
