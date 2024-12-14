package fr.univlille.algoknn;

import java.util.Collection;

public interface Distance<T>{
    double distance(T data1, T data2, Collection<String> caracteristiques);
}
