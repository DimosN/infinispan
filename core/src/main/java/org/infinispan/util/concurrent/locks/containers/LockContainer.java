package org.infinispan.util.concurrent.locks.containers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * A container for locks
 *
 * @author Manik Surtani
 * @author Mircea.Markus@jboss.com
 * @since 4.0
 */
public interface LockContainer<L extends Lock> {
   /**
    * Tests if a give owner owns a lock on a specified object.
    *
    * @param key   object to check
    * @param owner owner to test
    * @return true if owner owns lock, false otherwise
    */
   boolean ownsLock(Object key, Object owner);

   /**
    * @param key object
    * @return true if an object is locked, false otherwise
    */
   boolean isLocked(Object key);

   /**
    * @param key object
    * @return the lock for a specific object.  May be null if the object is not locked, but may also be an unlocked lock.
    */
   L getLock(Object key);

   /**
    * @return number of locks held
    */
   int getNumLocksHeld();

   /**
    * @return the size of the shared lock pool
    */
   int size();

   /**
    * Attempts to acquire a lock for the given object within certain time boundaries defined by the timeout and
    * time unit parameters.
    *
    * @param key Object to acquire lock on
    * @param timeout Time after which the lock acquisition will fail
    * @param unit Time unit of the given timeout
    * @return If lock was acquired it returns the corresponding Lock object. If lock was not acquired, it returns null
    * @throws InterruptedException If the lock acquisition was interrupted
    */
   L acquireLock(Object lockOwner, Object key, long timeout, TimeUnit unit) throws InterruptedException;

   /**
    * Release lock on the given key.
    *
    * @param key Object on which lock is to be removed
    */
   void releaseLock(Object lockOwner, Object key);

   /**
    * Returns the 'id' of the lock that will be used to guard access to a given key in the cache.  Particularly useful
    * if Lock Striping is used and locks may guard more than one key.  This mechanism can be used to check whether
    * keys may end up sharing the same lock.
    * <p />
    * If lock-striping is not used, the identity hash code of the lock created for this specific key is returned, if the
    * key is locked, or -1 if the key is not locked and a lock does not exist for the key.
    *
    * @param key key to test for
    * @return the ID of the lock.
    */
   int getLockId(Object key);
}
