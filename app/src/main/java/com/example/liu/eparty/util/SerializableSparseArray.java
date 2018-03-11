package com.example.liu.eparty.util;

import android.util.SparseArray;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializableSparseArray<E> extends SparseArray<E> implements Serializable {

    private static final long serialVersionUID = 824056059663678000L;

    public SerializableSparseArray(){
        super();
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        Object[] data = new  Object[size()];
        for (int i=data.length-1;i>=0;i--){
            Object[] pair = {keyAt(i),valueAt(i)};
            data[i] = pair;
        }
        oos.writeObject(data);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        Object[] data = (Object[]) ois.readObject();
        for (int i=data.length-1;i>=0;i--){
            Object[] pair = (Object[]) data[i];
            this.append((Integer)pair[0],(E)pair[1]);
        }
    }
}