# Rate Limiting Strategies in Java

## Overview
Rate limiting is a crucial technique to control the rate of incoming requests in distributed systems, preventing abuse and ensuring fair resource allocation. This document explores various rate-limiting algorithms, their advantages, disadvantages, and use cases.

---

## Table of Contents
1. [Fixed Window Counter](#fixed-window-counter)
2. [Sliding Window Log](#sliding-window-log)
3. [Leaky Bucket](#leaky-bucket)
4. [Token Bucket](#token-bucket)
5. [Implementation Considerations](#implementation-considerations)
6. [References](#references)

---

## Fixed Window Counter
The **Fixed Window Counter** divides time into fixed-size windows and counts the number of requests in each window. If the limit is exceeded, additional requests are blocked until the next window.

### **Key Idea**
- Track the number of requests in a fixed time window (e.g., 1 minute).
- Reset the counter at the start of a new window.

### **Pros**
✅ Simple and easy to implement  
✅ Efficient under low to moderate loads  
✅ Low memory and computational overhead  

### **Cons**
❌ Can allow bursts of traffic at window edges (e.g., requests spiking at the end of one window and the start of the next)  
❌ Inflexible and doesn't smooth out bursts  

### **Use Case**
- Suitable for applications where strict rate limiting is not required, and simplicity is preferred.

---

## Sliding Window Log
The **Sliding Window Log** method maintains a log of request timestamps and ensures that the count remains within a rolling window.

### **Key Idea**
- Maintain a queue of timestamps for each request.
- Remove old timestamps outside the current window.
- Use the queue size to track active requests.

### **Pros**
✅ Smooth request distribution over time  
✅ Prevents burstiness at window edges  
✅ More accurate than fixed window for controlling traffic  

### **Cons**
❌ Requires more memory to store timestamps  
❌ Higher computational overhead due to timestamp management  

### **Use Case**
- Ideal for applications requiring precise rate limiting and smooth traffic distribution.

---

## Leaky Bucket
The **Leaky Bucket** algorithm allows requests to flow at a constant rate, dropping excess requests if the bucket overflows.

### **Key Idea**
- Requests enter a queue (bucket) at a variable rate.
- Requests leave the bucket at a constant rate (leak).
- Overflowing requests are discarded or delayed.

### **Pros**
✅ Smooths out bursts of traffic  
✅ Ensures a steady rate of processing  
✅ Simple to implement  

### **Cons**
❌ Can introduce delays if the bucket is full  
❌ Less responsive to sudden traffic changes  

### **Use Case**
- Suitable for applications requiring a steady output rate, such as API gateways or network traffic shaping.

---

## Token Bucket
The **Token Bucket** algorithm refills tokens at a constant rate. Each request consumes a token; if no tokens are available, the request is rejected or delayed.

### **Key Idea**
- Tokens are added to the bucket at a steady rate.
- Each request consumes a token.
- If no tokens remain, requests are delayed or dropped.

### **Pros**
✅ Allows controlled bursts of traffic  
✅ Adaptable by adjusting bucket size and refill rate  
✅ More flexible than leaky bucket  

### **Cons**
❌ More complex implementation  
❌ Requires additional resources for token tracking  

### **Use Case**
- Ideal for applications requiring burst handling, such as streaming services or APIs with variable traffic patterns.

---

## Implementation Considerations
When implementing rate limiting in Java, consider the following:
1. **Concurrency**: Ensure thread safety when updating counters or token buckets in multi-threaded environments.
2. **Persistence**: For distributed systems, use a shared data store (e.g., Redis) to maintain rate-limiting state across instances.
3. **Configuration**: Allow dynamic configuration of rate limits, window sizes, and bucket sizes for flexibility.
4. **Monitoring**: Track rate-limiting metrics (e.g., rejected requests) to identify abuse or adjust limits.

### Example Libraries
- **Guava RateLimiter**: Provides token bucket-based rate limiting.
- **Resilience4j**: Offers rate-limiting and other fault-tolerance features.
- **Redis**: Can be used to implement distributed rate limiting.

---

## References
1. [Implementing Rate Limiting in Java – Fixed Window and Sliding Window](https://medium.com/@devenchan/implementing-rate-limiting-in-java-from-scratch-fixed-window-and-sliding-window-implementation-a6e8d6407d17)
2. [Guava RateLimiter Documentation](https://github.com/google/guava/wiki/RateLimiterExplained)
3. [Resilience4j Rate Limiter](https://resilience4j.readme.io/docs/ratelimiter)

---

This document provides a comprehensive overview of rate-limiting strategies in Java, helping you choose the right approach for your application. For further details, refer to the linked resources and example libraries.