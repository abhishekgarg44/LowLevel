Reference:

	https://medium.com/@devenchan/implementing-rate-limiting-in-java-from-scratch-fixed-window-and-sliding-window-implementation-a6e8d6407d17

Fixed Window Counter

	The fixed window counter is the simplest form of rate limiting. It divides time into fixed-size windows and counts the number of requests in each window, blocking any requests that exceed the limit.
	
The key here is the checking the time difference between current request and previous request and reset the window counter.

Pros:

	Simplicity: Easy to implement and understand.
	Performance: Efficient under low to moderate load due to minimal computational overhead.
Cons:

	Burstiness at window edges: Can allow twice the rate limit of traffic if requests come in bursts at the boundary between two windows.
	Inflexibility: Does not account for varying request rates or smooth out bursts over time.


Sliding Window Log

	The sliding window log offers a more refined approach, allowing request limits to be distributed more evenly over time. It logs the timestamp of each request and ensures that the count is only for the current window.

The key here is polling out the requests out side of the window, and use the queue size to track the request coutner within the window.

Pros:

	Smooth Rate Limiting: More evenly distributes requests by considering the exact timestamps of incoming requests.
	Fairness: Prevents the burstiness issue seen at the boundaries in the fixed window approach.
Cons:

	Memory Intensive: Requires storing timestamps for each request, which can consume more memory.
	Computationally More Intensive: Requires more computation to manage and evaluate the log of requests.

Leaky Bucket Rate Limiter:

	The leaky bucket algorithm metaphorically allows requests to drip out of a bucket at a constant rate. If the bucket (buffer) overflows, new requests are discarded.
Pros:

	Smooths Bursts: Effectively smoothens out bursts of traffic over time, allowing for handling sudden spikes more gracefully.
	Consistent Output Rate: Ensures that the data processing occurs at a steady rate.
Cons:

	Potentially Delaying: Can introduce delays in processing if the bucket is consistently full, leading to a queue of requests.
	Less Reactive: Not as responsive to changes in incoming traffic patterns due to its smoothing nature.


Token Bucket Rate Limiter:

	The token bucket algorithm fills the bucket with tokens at a constant rate. Each request removes a token, and if no tokens are available, the request is either delayed or rejected.
	
Pros:

	Flexibility: Allows for a certain amount of burstiness while still enforcing an overall limit, offering a balance between the fixed window and leaky bucket models.
	Adaptive: Can be configured to adapt to varying load by adjusting the refill rate and bucket size.
Cons:

	Complexity: More complex to implement correctly, especially in distributed systems.
	Resource Intensive: Requires maintaining token counts and timestamps, which could be demanding on resources at very high scales.
