# Analytical Report: MST Algorithms Performance Analysis

## 1. Detailed Analysis of Input Data and Algorithm Results

### Experimental Dataset Composition
**Total: 28 graphs across 4 categories**

#### Small Graphs (5 graphs: ID 1-5)
- **Vertex Range**: 8-29 vertices
- **Edge Range**: 19-160 edges
- **Density**: Moderate to high (edge-to-vertex ratio: 2.4-6.6)

#### Medium Graphs (10 graphs: ID 6-15)
- **Vertex Range**: 35-299 vertices
- **Edge Range**: 198-15,269 edges
- **Density**: Highly variable (ratio: 5.7-53.6)

#### Large Graphs (10 graphs: ID 16-25)
- **Vertex Range**: 320-999 vertices
- **Edge Range**: 9,928-96,200 edges
- **Density**: Extreme variation (ratio: 31-108)

#### Extra Large Graphs (3 graphs: ID 26-28)
- **Vertex Range**: 1,050-1,850 vertices
- **Edge Range**: 48,872-187,796 edges
- **Density**: Moderate to high (ratio: 46-101)

### Performance Analysis by Graph Category

#### Small Graphs Performance
**Observations:**

- **Graph 1**: Prim faster (1.04ms vs 1.94ms) - Prim: 49 operations vs Kruskal: 103 operations (Prim more operationally efficient)
- **Graph 2**: Kruskal faster (0.27ms vs 0.24ms) - Prim: 86 operations vs Kruskal: 225 operations (Prim more operationally efficient)
- **Graph 3**: Kruskal faster (0.29ms vs 0.24ms) - Prim: 136 operations vs Kruskal: 422 operations (Prim more operationally efficient)
- **Graph 4**: Prim faster (0.61ms vs 0.73ms) - Prim: 267 operations vs Kruskal: 1129 operations (Prim more operationally efficient)
- **Graph 5**: Kruskal faster (0.70ms vs 0.66ms) - Prim: 297 operations vs Kruskal: 1201 operations (Prim more operationally efficient)

**Analysis**: Prim's algorithm consistently used significantly fewer operations across all small graphs, demonstrating better operational efficiency despite sometimes slower execution times due to more computationally expensive operations.

#### Medium Graphs Performance (ID 6-15)
**Critical Findings:**
- **Graph 7-9**: Prim faster despite more edges (2.19ms vs 3.01ms, 3.12ms vs 4.05ms, 4.17ms vs 5.76ms)
- **Graph 10-12**: Kruskal significant advantage (9.29ms vs 5.99ms, 11.41ms vs 6.05ms, 14.00ms vs 8.71ms)
- **Turning Point**: Around 200 vertices, Kruskal begins outperforming Prim consistently

**Operation Efficiency:**
- **Graph 7**: Prim - 1,232 operations vs Kruskal - 6,660 operations (Prim more efficient)
- **Graph 8**: Prim - 1,979 operations vs Kruskal - 12,486 operations (Prim more efficient)
- **Graph 9**: Prim - 3,576 operations vs Kruskal - 23,671 operations (Prim more efficient)
- **Graph 10**: Prim - 8,475 operations vs Kruskal - 69,367 operations (Prim more efficient)
- **Graph 11**: Prim - 10,099 operations vs Kruskal - 91,927 operations (Prim more efficient)
- **Graph 12**: Prim - 13,625 operations vs Kruskal - 125,344 operations (Prim more efficient)

**Analysis**: Prim's algorithm maintained significantly better operational efficiency across all medium graphs, using 5-10 times fewer operations than Kruskal. However, Kruskal's operations are computationally cheaper, allowing it to achieve better execution times despite higher operation counts, especially as graph size increases beyond 200 vertices. The union-find efficiency in Kruskal scales better with moderate-sized graphs.

#### Large Graphs Performance (ID 16-25)
**Consistent Pattern:**
- **Graph 16-19**: Mixed results
- **Graph 20**: Kruskal winner (43.92ms vs 19.73ms) - 2.2x faster
- **Graph 21**: Prim winner (22.46ms vs 28.80ms)
- **Graph 22-25**: Kruskal generally faster

**Analysis**: Kruskal's O(E log E) complexity handles edge-rich graphs efficiently, but Prim can win on specific graph structures with moderate density.

**Operation Count Analysis**
**Consistent Finding Across All Categories:**
- Prim's algorithm used significantly fewer operations in all test cases
- Average operation ratio: Prim 35,197 vs Kruskal 384,372 (≈11:1 ratio)
- Key Insight: Prim maintains better operational efficiency, but Kruskal's cheaper operations often lead to competitive performance. On very large graphs, Prim's efficiency translates directly to superior execution times.

#### Extra Large Graphs (ID 26-28)
**Final Pattern:**
- **Graph 26**: Nearly equal performance (26.61ms vs 26.68ms)
- **Graph 27**: Prim significantly faster (39.05ms vs 83.31ms) - 2.1x faster
- **Graph 28**:Prim significantly faster (94.45ms vs 159.67ms) - 1.7x faster
- **Memory Considerations**: Prim's adjacency list structure provides better cache locality at extreme scales, as it accesses memory sequentially rather than randomly jumping between distant memory locations like Kruskal's union-find operations.

**Operation Efficiency:**
- **Graph 26**: Prim - 56,745 operations vs Kruskal - 554,138 operations (Prim more efficient)
- **Graph 27**: Prim - 92,849 operations vs Kruskal - 1,005,994 operations (Prim more efficient)
- **Graph 28**: Prim - 202,016 operations vs Kruskal - 2,473,629 operations (Prim more efficient)

**Analysis**: For extra large graphs (>1000 vertices), Prim demonstrates both better execution time and operational efficiency. Prim's adjacency structure provides better cache performance at extreme scales, while Kruskal's union-find operations become increasingly expensive due to the depth of trees and memory access patterns.

### Operation Count Analysis
**Consistent Finding Across All Categories:**
- Prim's algorithm used significantly fewer operations in all test cases
- **Average operation ratio**: Prim 35,197 vs Kruskal 384,372 (≈11:1 ratio)
- **Key Insight**: Kruskal achieves competitive performance despite higher operation counts due to computationally cheaper operations, **except on very large graphs where Prim's efficiency translates to better execution times**

## 2. Theory vs Practice: Algorithm Efficiency Comparison

### Theoretical Foundation
**Sources:**
- **GeeksforGeeks** - "Prim's Algorithm using Priority Queue"
- **Baeldung** - "Kruskal's Algorithm for Spanning Tree"
- **Wikipedia** - "Disjoint-set data structure"

**Theoretical Complexities:**
- **Prim with Binary Heap**: O(E log V)
- **Kruskal with Union-Find**: O(E log E)
- **Space Complexity**: Prim O(V), Kruskal O(E)

### Practical Implementation Analysis

#### Prim's Algorithm Implementation
**My Implementation Choices:**
- Priority queue using Java's PriorityQueue
- Adjacency list graph representation
- Lazy deletion approach

**Practical vs Theoretical:**
- **Expected**: Good performance on dense graphs
- **Observed**: Excellent performance on very large graphs
- **Key Finding**: Outperforms Kruskal on graphs with >1000 vertices

#### Kruskal's Algorithm Implementation
**My Implementation Choices:**
- Union-Find with path compression and union by rank
- Edge list representation
- Java's Collections.sort() for edge sorting

**Practical vs Theoretical:**
- **Expected**: O(E log E) due to sorting dominance
- **Observed**: Excellent scaling with graph size
- **Strength**: Simpler implementation with consistent results

### Critical Experimental Finding
The most significant discovery: Prim's algorithm demonstrates superior performance on very large graphs (V > 1000), contradicting the common expectation that Kruskal should scale better. This highlights how practical implementation factors like memory access patterns can outweigh theoretical complexity analysis.

### Practical Insights

**Performance Patterns Observed:**
- Prim: Better operational efficiency (fewer operations)
- Kruskal: Often faster despite more operations (medium graphs)
- Scale Matters: Prim wins on very large graphs (>1000 vertices)

**Implementation Experience:**
- Kruskal: Simpler to implement and debug
- Prim: More complex due to priority queue management
- Java: Both benefit from optimized standard collections

## 3. Conclusions and Algorithm Selection Guidelines

### Based on Experimental Results
**For Small Graphs (V < 100):**
- Mixed results - test both algorithms
- Prim often better operationally, Kruskal sometimes faster

**For Medium Graphs (100-500 vertices):**
- Kruskal generally preferred
- Better scaling with graph size
- Simpler implementation

**For Large Graphs (500-1000 vertices):**
- Kruskal recommended for edge-rich graphs
- Test both for specific use cases

**For Very Large Graphs (V > 1000):**
- Prim strongly recommended
- Demonstrates best performance on largest test cases
- Better cache efficiency at scale

### Implementation Recommendations
**For Learning/Education:**
- Kruskal - simpler to understand and implement
- Clear separation of sorting and union-find steps

**For Production Systems:**
- Profile both algorithms on real data
- Consider Prim for very large datasets
- Consider Kruskal for maintainability

### Key Research Findings
**Operational Efficiency:**
- Prim uses significantly fewer operations (11:1 ratio)
- Kruskal's operations are computationally cheaper

## 4. References

1. **GeeksforGeeks** - "Prim's Algorithm using Priority Queue"
   https://www.geeksforgeeks.org/prims-algorithm-using-priority_queue-stl/

2. **Baeldung** - "Kruskal's Algorithm for Spanning Tree"
   https://www.baeldung.com/java-spanning-trees-kruskal

3. **Wikipedia** - "Disjoint-set data structure"
   https://en.wikipedia.org/wiki/Disjoint-set_data_structure

4. **Graphviz Documentation** - Graph visualization tool
   https://graphviz.org/documentation/