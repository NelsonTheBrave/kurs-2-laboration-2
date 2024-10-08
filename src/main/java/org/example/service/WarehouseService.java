package org.example.service;

import org.example.entities.Category;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public class WarehouseService {
    @FunctionalInterface
    public interface WarehouseOperation {
        void execute();
    }
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    public <T> T performOperation(Supplier<T> operation, boolean isWrite) {
        if (isWrite) {
            lock.writeLock().lock();
        } else {
            lock.readLock().lock();
        }
        try {
            return operation.get();
        } finally {
            if (isWrite) {
                lock.writeLock().unlock();
            } else {
                lock.readLock().unlock();
            }
        }
    }
}
