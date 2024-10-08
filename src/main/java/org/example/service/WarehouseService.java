package org.example.service;

import org.example.entities.Category;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public class WarehouseService {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public <T> T performReadOperation(Supplier<T> operation) {
        lock.readLock().lock();
        try {
            return operation.get();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void performWriteOperation(Runnable operation) {
        lock.writeLock().lock();
        try {
            operation.run();
        } finally {
            lock.writeLock().unlock();
        }
    }
}