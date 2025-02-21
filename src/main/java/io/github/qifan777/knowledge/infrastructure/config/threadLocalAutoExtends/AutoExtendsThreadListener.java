package io.github.qifan777.knowledge.infrastructure.config.threadLocalAutoExtends;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public interface AutoExtendsThreadListener<T> {
    Supplier<T> supply();
    void beforeThreadRun(T t);
    void onThreadDone(T t);

    class Registry {
        public static final ThreadFactory Thread_FACTORY = new AutoExtendsThreadLocalThreadFactory();
        @SuppressWarnings("rawtypes")
        public static void register(AutoExtendsThreadListener listener) {
            AutoExtendsThreadLocalThreadFactory.LISTENERS.add(listener);
        }
        private static class AutoExtendsThreadLocalThreadFactory implements ThreadFactory {
            private static final List<AutoExtendsThreadListener<?>> LISTENERS = new LinkedList<>();
            @Override
            public Thread newThread(@NotNull Runnable r) {
                return new AutoExtendsThreadLocalThread(r);
            }

            private static class AutoExtendsThreadLocalThread extends Thread {
                public AutoExtendsThreadLocalThread(Runnable target) {
                    super(target);
                }

                private List<Object> values;
                private List<AutoExtendsThreadListener<?>> listeners;
                @Override
                public synchronized void start() {
                    listeners = new ArrayList<>(AutoExtendsThreadLocalThreadFactory.LISTENERS);
                    values = new LinkedList<>();
                    listeners.stream()
                            .map(AutoExtendsThreadListener::supply)
                            .map(Supplier::get).forEach(values::add);
                    super.start();
                }

                @Override
                public void run() {
                    try {
                        if (!listeners.isEmpty()) {
                            callListenersBeforeRun();
                        }
                        super.run();
                    } finally {
                        if (! listeners.isEmpty()) {
                            callListenersOnDone();
                        }
                    }
                }

                private void callListenersBeforeRun() {
                    AtomicInteger counter = new AtomicInteger(0);
                    loop(values, (hasNext, value) -> {
                        int index = counter.getAndAdd(1);
                        ((AutoExtendsThreadListener<Object>) listeners.get(index))
                                .beforeThreadRun(value);
                    });
                    listeners.clear();
                    values.clear();
                }

                private void callListenersOnDone() {
                    AtomicInteger counter = new AtomicInteger(0);
                    loop(values, (hasNext, value) -> {
                        int index = counter.getAndAdd(1);
                        ((AutoExtendsThreadListener<Object>) listeners.get(index))
                                .onThreadDone(value);
                    });
                    listeners.clear();
                    values.clear();
                }

                private static <T> void loop(Iterable<T> iterable, BiConsumer<Boolean, T> hasNextAndObjectConsumer) {
                    Iterator<T> it = iterable.iterator();
                    while (it.hasNext()) {
                        try {
                            while (it.hasNext()) {
                                T next = it.next();
                                hasNextAndObjectConsumer.accept(it.hasNext(), next);
                            }
                        } catch (Throwable ignored) {}
                    }
                }
            }
        }
    }
}
