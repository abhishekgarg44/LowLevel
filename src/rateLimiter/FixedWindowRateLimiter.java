package rateLimiter;

public class FixedWindowRateLimiter implements RateLimiter {
    // The maximum number of requests allowed within the window.
    private final int threshold;
    // The start time of the current window.
    private volatile long windowStartTime;
    // Duration of the window in milliseconds, set to one second.
    private final long windowUnit = 1000L;
    // Counter for the number of requests in the current window.
    private final AtomicInteger counter = new AtomicInteger();

    /**
     * Constructs a FixedWindowRateLimiter with the specified threshold.
     *
     * @param threshold the maximum number of requests allowed per window
     */
    public FixedWindowRateLimiter(int threshold) {
        this.threshold = threshold;
        this.windowStartTime = System.currentTimeMillis();
    }

    /**
     * Attempts to acquire a permit for a request.
     *
     * @return true if the request is within the threshold limit; false otherwise.
     */
    @Override
    public boolean tryAcquire() {
        long currentTime = System.currentTimeMillis();
        // If the current time exceeds the window start time plus the window unit,
        // it means we've entered a new window. Reset the counter and update the window start time.
        if (currentTime - windowStartTime >= windowUnit) {
            // Double-check to prevent race condition issues in a multi-threaded environment.
            if (currentTime - windowStartTime >= windowUnit) {
                counter.set(0);
                windowStartTime = currentTime;
            }
        }

        // Increment the counter and check if it exceeds the threshold.
        // If the counter is within the threshold, grant access; otherwise, reject the request.
        return counter.incrementAndGet() <= threshold;
    }
}
