package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class MyBlockingQueueImpl<E> implements BlockingQueue<E> {
	private LinkedList<E> queue = new LinkedList<>();
	private int capacity;
	private Lock lock = new ReentrantLock();
	private Condition producersWaitingCondition = lock.newCondition();
	private Condition consumersWaitingCondition = lock.newCondition();
	
	public MyBlockingQueueImpl(int capacity) {
		this.capacity = capacity;
	}
	public MyBlockingQueueImpl() {
		this(Integer.MAX_VALUE);
	}
	@Override
	public E remove() throws NoSuchElementException {
		lock.lock();
		try {
			if(queue.isEmpty()) {
				throw new NoSuchElementException();
			}else {
				return queue.removeFirst();
			}
		}
		finally {
			producersWaitingCondition.signal(); 
			lock.unlock();
		}
	}

	@Override
	public E poll() {
		lock.lock();
		try {
			if (queue.isEmpty()) {
				return null;
			}
			return queue.removeFirst();
		} finally {
			producersWaitingCondition.signal();
			lock.unlock();
		}
	}

	@Override
	public E element() {
		lock.lock();
		try {
			if(queue.isEmpty()) {
				throw new NoSuchElementException();
			}else {
				return queue.getFirst();
			}
		}finally {
			lock.unlock();
		}
	}

	@Override
	public E peek() {
		lock.lock();
		try {
			return queue.getFirst();
		}finally {
			lock.unlock();
		}
	}

	@Override
	public int size() {
		lock.lock();
		try {
			return queue.size();	
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public boolean isEmpty() {
		lock.lock();
		try {
			return queue.size()==0;	
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public Iterator<E> iterator() {
		lock.lock();
		try {
			return queue.iterator();
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public Object[] toArray() {
		lock.lock();
		try {
			return queue.toArray();
		}finally {
			lock.unlock();
		}
	}

	@Override
	public <T> T[] toArray(T[] a) {
		lock.lock();
		try {
			return queue.toArray(a) ;
		}finally {
			lock.unlock();
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		lock.lock();
		try {
			return queue.containsAll(c) ;
		}finally {
			lock.unlock();
		}	
		}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		lock.lock();
		int cSize = c.size();
		int remCap = this.remainingCapacity();
		if(cSize > remCap) {
			throw new IllegalStateException();
		}
		try {
			return queue.addAll(c) ;
		}finally {
			if(cSize<remCap) {
				while(0<cSize--) {
				consumersWaitingCondition.signal();
				}
			}
			lock.unlock();
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		lock.lock();
		try {
			return queue.removeAll(c) ;
		}
		finally {
			producersWaitingCondition.signal();
			lock.unlock();
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		lock.lock();
		try {
			return queue.retainAll(c) ;
		}finally {
			lock.unlock();
		}
	}

	@Override
	public void clear() {
		try {
			lock.lock();
			queue.clear();
		} finally {
			lock.unlock();
		}		

	}

	@Override
	public boolean add(E e) {
		if (e == null) {
			throw new IllegalArgumentException();
		}
		lock.lock();
		try {
			if (queue.size()>=capacity) {
				throw new IllegalStateException();
			}
			queue.addLast(e);
			return true;
		} finally {
			consumersWaitingCondition.signal();
			lock.unlock();
		}
	}

	@Override
	public boolean offer(E e) {
		if (e == null) {
			throw new IllegalArgumentException();
		}
		lock.lock();
		try {
			if (queue.size()>=capacity) {
				return false;
			}
			queue.addLast(e);
			consumersWaitingCondition.signal();
			return true;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void put(E e) throws InterruptedException {
		if (e == null) {
			throw new IllegalArgumentException();
		}
		lock.lock();
		try {
			while (queue.size() >= capacity) {
				producersWaitingCondition.await();
			}
			queue.addLast(e);
			consumersWaitingCondition.signal();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		if (e == null || timeout < 0) {
			throw new IllegalArgumentException();
		}
		lock.lock();
		try {
			Instant start = Instant.now();
			long timeoutNanos = unit.toNanos(timeout);
			while(ChronoUnit.NANOS.between(start, Instant.now())<timeoutNanos) {
				if(queue.size()<capacity) {
					queue.addLast(e);
					consumersWaitingCondition.signal();
					return true;
				}
			}
				return false;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public E take() throws InterruptedException {
		lock.lock();
		try {
			while (queue.isEmpty()) {
				consumersWaitingCondition.await();
			}
			return queue.removeFirst();
		} finally {
			producersWaitingCondition.signal(); 
			lock.unlock();
		}
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		lock.lock();
		try {
			Instant start = Instant.now();
			long timeoutNanos = unit.toNanos(timeout);
			while(ChronoUnit.NANOS.between(start, Instant.now())<timeoutNanos) {
				if(!queue.isEmpty()) {
					return queue.removeFirst();
				}
			}
				return null;
		} finally {
			producersWaitingCondition.signal();
			lock.unlock();
		}
	}

	@Override
	public int remainingCapacity() {
		lock.lock();
		try {
			return capacity - queue.size();
		}finally {
			lock.unlock();
		}
	}

	@Override
	public boolean remove(Object o) {
		//No implement
		return false;
	}

	@Override
	public boolean contains(Object o) {
		lock.lock();
		try {
			return queue.contains(o);
		}finally {
			lock.unlock();
		}
	}

	@Override
	public int drainTo(Collection<? super E> c) {
		//No implement
		return 0;
	}

	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		//No implement
		return 0;
	}
}