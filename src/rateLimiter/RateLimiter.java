package rateLimiter;

public interface RateLimiter {
    boolean tryAcquire();
}
