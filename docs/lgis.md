# lgis

## 最長増加部分文字列(LIS)

### LISの長さを求めるアルゴリズム

#### 二分探索法を使用するアルゴリズム

##### アルゴリズム

このアルゴリズムで、LISの長さを求めることができる。ただし、LISの内容自体は求めることができない。

元の配列を $A$ とする。 $A$ の配列の長さを $N$ とする。

- 空の配列 $L$ を用意する。
- $A$ の要素を、 $i = 0,1,...,N-1$ の順に一つずつ処理する。各 $A[i]$ に対して、二分探索で $L$ 内に挿入する位置 $pos$ を見つける。
  - $|L| > pos$ の場合、 $L[pos]$ を $A[i]$ で置き換える。
  - $|L| = pos$ の場合、 $A[i]$ を $L$ の末尾に追加する。
- 最後に、 $L$ の長さがLISの長さになる。

##### 具体例

配列 $A = [3, 4, 5, 1, 6, 3, 2, 7]$ を使用する。

- $L = []$ からスタート
- $A[0] = 3$ , $L = []$
  - $|L| = 0$ ,  $pos = 0$ より、 $|L| = pos$
    - $L$ の末尾に、 $A[0] = 3$ を追加
  - $L = [3]$
- $A[1] = 4$ , $L = [3]$
  - $|L| = 1$ ,  $pos = 1$ より、 $|L| = pos$
    - $L$ の末尾に、 $A[1] = 4$ を追加
  - $L = [3, 4]$
- $A[2] = 5$ , $L = [3, 4]$
  - $|L| = 2$ ,  $pos = 2$ より、 $|L| = pos$
    - $L$ の末尾に、 $A[2] = 5$ を追加
  - $L = [3, 4, 5]$
- $A[3] = 1$ , $L = [3, 4, 5]$
  - $|L| = 3$ ,  $pos = 0$ より、 $|L| > pos$
    - $L[0]$ を、 $A[3] = 1$ に置換
  - $L = [1, 4, 5]$
- $A[4] = 6$ , $L = [1, 4, 5]$
  - $|L| = 3$ ,  $pos = 3$ より、 $|L| = pos$
    - $L[0]$ の末尾に、 $A[4] = 6$ を追加
  - $L = [1, 4, 5, 6]$
- $A[5] = 3$ , $L = [1, 4, 5, 6]$
  - $|L| = 4$ ,  $pos = 1$ より、 $|L| > pos$
    - $L[1]$ を、 $A[5] = 3$ に置換
  - $L = [1, 3, 5, 6]$
- $A[6] = 2$ , $L = [1, 3, 5, 6]$
  - $|L| = 4$ ,  $pos = 1$ より、 $|L| > pos$
    - $L[1]$ を、 $A[6] = 2$ に置換
  - $L = [1, 2, 5, 6]$
- $A[7] = 7$ , $L = [1, 2, 5, 6]$
  - $|L| = 4$ ,  $pos = 4$ より、 $|L| = pos$
    - $L$ の末尾に、 $A[7] = 7$ を置換
  - $L = [1, 2, 5, 6, 7]$

結果として、LISの長さは $5$ である。

##### コード

```clojure
(defn bisect-left
  "Finds the insertion index for x in the sorted vector `vec`."
  [vec x]
  (loop [low 0
         high (count vec)]
    (if (>= low high)
      low
      (let [mid (quot (+ low high) 2)]
        (if (>= (nth vec mid) x)
          (recur low mid)
          (recur (inc mid) high))))))

(defn lis-length
  "Finds the length of the longest increasing subsequence (LIS) in a vector `A`."
  [A]
  (loop [i 0 L []]  ;; This will store the current LIS structure
    (if (< i (count A))
      (let [a (nth A i)
            pos (bisect-left L a)]  ;; Determine where to place A[i] in L
        (recur (inc i)
               (if (< pos (count L))
                 (assoc L pos a)  ;; Replace the element in L at position `pos`
                 (conj L a))))    ;; Add the element to the end of L
      (count L))))  ;; The length of L is the length of the LIS

(lis-length [3 4 5 1 6 3 2 7])
;; => 5
;; (L = [1, 2, 5, 6, 7])
```

- `bisect-left`
  - ソートされたリスト `L` 内で要素 `x` を挿入する適切な index を見つける。 `L` の中で `x` 以上の最初の要素の index を返す。

```clojure
(bisect-left [1 3 5 6] 0)
;; => 0

(bisect-left [1 3 5 6] 1)
;; => 0

(bisect-left [1 3 5 6] 2)
;; => 1

(bisect-left [1 3 5 6] 3)
;; => 1

(bisect-left [1 3 5 6] 6)
;; => 3

(bisect-left [1 3 5 6] 7)
;; => 4
```

##### LISの長さが求められる理由

- 部分列の管理
  - $L[k]$ は「長さ $k+1$ の増加部分列の末尾にある最小値」を保持する。この構造により、 $A[i]$ が「どの位置に挿入できるか」を効率的に追跡できる。
- 要素の追加と置換
  - 各要素 $A[i]$ に対して、 `bisect-left` によって $L$ 内の適切な位置を探す。
  - $A[i]$ が $L$ の最後の要素より大きければ、 $L$ を拡張し、部分列の長さを延長する。
  - もし $A[i]$ が $L$ のどこかの要素より小さいか等しい場合、 $L$ のその位置に $A[i]$ を挿入する。これは、「長さ $k+1$ の部分列の末尾にある最小の値を保持する」という性質を守るため。
- 正しいLISの長さ
  - $L$ の各要素は、可能な部分列の末尾の最小値を表す。そのため、 $L$ の長さがそのままLISの長さに相当する。

##### 計算量

$O(N\log{N})$

### LISの内容を求めるアルゴリズム

#### 二分探索法を使用するアルゴリズム

##### アルゴリズム

- $prev$: 各要素 $A[i]$ がLISに含まれたとき、その直前の要素のインデックスを保持する。これを使って、LISを逆順に辿り、最終的なLISの内容を復元する。
- $\text{index-in-lis}$: LISに含まれる要素のインデックスを追跡する。

**LISの追跡と更新**:

- 空の配列 $L$, $\text{index-in-lis}$ , 要素数が $N$ で全ての要素が $-1$ の配列 $prev$ を用意する。
- $A$ の要素を、 $i = 0,1,...,N-1$ の順に一つずつ処理する。各 $A[i]$ に対して、二分探索で $L$ 内に挿入する位置 $pos$ を見つける。
  - $L$ の更新
    - $|L| > pos$ の場合、 $L[pos]$ を $A[i]$ で置換する。
    - $|L| = pos$ の場合、 $L$ の末尾に $A[i]$ を 追加する。
  - $\text{index-in-lis}$ の更新
    - $|L| > pos$ の場合、 $\text{index-in-lis}[pos]$ を $i$ で置換する。
    - $|L| = pos$ の場合、 $\text{index-in-lis}$ の末尾に $i$ を追加する。
  - $prev$ の更新
    - $pos = 0$ の場合、何もしない ( $prev[i] = -1$ のまま )
    - $pos \geq 1$ の場合、 $prev[i]$ を $\text{index-in-lis}[pos - 1]$ で置換する。

**LISの復元**:

- 空の配列 $lis$ と追跡用インデックス $index$ を用意する。
- $\text{index-in-lis}$ の 末尾の値を $index$ に代入する。
- 以下を $index = -1$ となるまで繰り返す。
  - $A[index]$ を $lis$ に追加する。
  - $index$ を $prev[index]$ に更新する。
- 得られた $lis$ を逆順にする。

##### 具体例

配列 $A = [3, 4, 5, 1, 6, 3, 2, 7]$ を使用する。

**LISの追跡と更新**:

- $L = []$ , $\text{index-in-lis} = []$ , $prev = [-1, -1, -1, -1, -1, -1, -1, -1]$ からスタート
- $A[0] = 3$ , $L = []$ , $\text{index-in-lis} = []$ , $prev = [-1, -1, -1, -1, -1, -1, -1, -1]$
  - $|L| = 0$ ,  $pos = 0$ より、 $|L| = pos$
  - $L$ の更新
    - $L$ の末尾に、 $A[0] = 3$ を追加
      - $L = [3]$
  - $\text{index-in-lis}$ の更新
    - $\text{index-in-lis}$ の末尾に、 $0$ を追加
      - $\text{index-in-lis} = [0]$
  - $prev$ の更新
    - 何もしない
      - $prev = [-1, -1, -1, -1, -1, -1, -1, -1]$
- $A[1] = 4$ , $L = [3]$ , $\text{index-in-lis} = [0]$ , $prev = [-1, -1, -1, -1, -1, -1, -1, -1]$
  - $|L| = 1$ ,  $pos = 1$ より、 $|L| = pos$
  - $L$ の更新
    - $L$ の末尾に、 $A[1] = 4$ を追加
      - $L = [3, 4]$
  - $\text{index-in-lis}$ の更新
    - $\text{index-in-lis}$ の末尾に、 $1$ を追加
      - $\text{index-in-lis} = [0, 1]$
  - $prev$ の更新
    - $prev[1]$ を $\text{index-in-lis}[1-1] = \text{index-in-lis}[0] = 0$ で置換
      - $prev = [-1, 0, -1, -1, -1, -1, -1, -1]$
- $A[2] = 5$ , $L = [3, 4]$ , $\text{index-in-lis} = [0, 1]$ , $prev = [-1, 0, -1, -1, -1, -1, -1, -1]$
  - $|L| = 2$ ,  $pos = 2$ より、 $|L| = pos$
  - $L$ の更新
    - $L$ の末尾に、 $A[2] = 5$ を追加
      - $L = [3, 4, 5]$
  - $\text{index-in-lis}$ の更新
    - $\text{index-in-lis}$ の末尾に、 $2$ を追加
      - $\text{index-in-lis} = [0, 1, 2]$
  - $prev$ の更新
    - $prev[1]$ を $\text{index-in-lis}[2-1] = \text{index-in-lis}[1] = 1$ で置換
      - $prev = [-1, 0, 1, -1, -1, -1, -1, -1]$
- $A[3] = 1$ , $L = [3, 4, 5]$ , $\text{index-in-lis} = [0, 1, 2]$ , $prev = [-1, 0, 1, -1, -1, -1, -1, -1]$
  - $|L| = 3$ ,  $pos = 0$ より、 $|L| > pos$
  - $L$ の更新
    - $L[0]$ を、 $A[3] = 1$ に置換
      - $L = [1, 4, 5]$
  - $\text{index-in-lis}$ の更新
    - $\text{index-in-lis}[0]$ を、 $3$ を置換
      - $\text{index-in-lis} = [3, 1, 2]$
  - $prev$ の更新
    - 何もしない
      - $prev = [-1, 0, 1, -1, -1, -1, -1, -1]$
- $A[4] = 6$ , $L = [1, 4, 5]$ , $\text{index-in-lis} = [3, 1, 2]$ , $prev = [-1, 0, 1, -1, -1, -1, -1, -1]$
  - $|L| = 3$ ,  $pos = 3$ より、 $|L| = pos$
  - $L$ の更新
    - $L$ の末尾に、 $A[4] = 6$ を追加
      - $L = [1, 4, 5, 6]$
  - $\text{index-in-lis}$ の更新
    - $\text{index-in-lis}$ の末尾に、 $4$ を置換
      - $\text{index-in-lis} = [3, 1, 2, 4]$
  - $prev$ の更新
    - $prev[4]$ を $\text{index-in-lis}[3-1] = \text{index-in-lis}[2] = 2$ で置換
      - $prev = [-1, 0, 1, -1, 2, -1, -1, -1]$
- $A[5] = 3$ , $L = [1, 4, 5, 6]$ , $\text{index-in-lis} = [3, 1, 2, 4]$ , $prev = [-1, 0, 1, -1, 2, -1, -1, -1]$
  - $|L| = 4$ ,  $pos = 1$ より、 $|L| > pos$
  - $L$ の更新
    - $L[1]$ を、 $A[5] = 3$ に置換
      - $L = [1, 3, 5, 6]$
  - $\text{index-in-lis}$ の更新
    - $\text{index-in-lis}[1]$ を、 $5$ を置換
      - $\text{index-in-lis} = [3, 5, 2, 4]$
  - $prev$ の更新
    - $prev[5]$ を $\text{index-in-lis}[1-1] = \text{index-in-lis}[0] = 3$ で置換
      - $prev = [-1, 0, 1, -1, 2, 3, -1, -1]$
- $A[6] = 2$ , $L = [1, 3, 5, 6]$ , $\text{index-in-lis} = [3, 5, 2, 4]$ , $prev = [-1, 0, 1, -1, 2, 3, -1, -1]$
  - $|L| = 4$ ,  $pos = 1$ より、 $|L| > pos$
  - $L$ の更新
    - $L[1]$ を、 $A[6] = 2$ に置換
      - $L = [1, 2, 5, 6]$
  - $\text{index-in-lis}$ の更新
    - $\text{index-in-lis}[1]$ を、 $6$ を置換
      - $\text{index-in-lis} = [3, 6, 2, 4]$
  - $prev$ の更新
    - $prev[6]$ を $\text{index-in-lis}[1-1] = \text{index-in-lis}[0] = 3$ で置換
      - $prev = [-1, 0, 1, -1, 2, 3, 3, -1]$
- $A[7] = 7$ , $L = [1, 2, 5, 6]$ , $\text{index-in-lis} = [3, 6, 2, 4]$ , $prev = [-1, 0, 1, -1, 2, 3, 3, -1]$
  - $|L| = 4$ ,  $pos = 4$ より、 $|L| = pos$
  - $L$ の更新
    - $L$ の末尾に、 $A[7] = 7$ を追加
      - $L = [1, 2, 5, 6, 7]$
  - $\text{index-in-lis}$ の更新
    - $\text{index-in-lis}$ の末尾に、 $7$ を置換
      - $\text{index-in-lis} = [3, 6, 2, 4, 7]$
  - $prev$ の更新
    - $prev[7]$ を $\text{index-in-lis}[4-1] = \text{index-in-lis}[3] = 4$ で置換
      - $prev = [-1, 0, 1, -1, 2, 3, 3, 4]$

結果:

- $L = [1, 2, 5, 6, 7]$
- $\text{index-in-lis} = [3, 6, 2, 4, 7]$
- $prev = [-1, 0, 1, -1, 2, 3, 3, 4]$

**LISの復元**:

- $lis = []$ , $index = nil$ からスタート
- $index$ に $\text{index-in-lis}[-1] = 7$ を代入する。
  - $index = 7$
- $lis = []$ , $index = 7$
  - $A[7] = 7$ を $lis$ に追加する
    - $lis = [7]$
  - $index$ を $prev[7] = 4$ に更新する
    - $index = 4$
- $lis = [7]$ , $index = 4$
  - $A[4] = 6$ を $lis$ に追加する
    - $lis = [7, 6]$
  - $index$ を $prev[4] = 2$ に更新する
    - $index = 2$
- $lis = [7, 6]$ , $index = 2$
  - $A[2] = 5$ を $lis$ に追加する
    - $lis = [7, 6, 5]$
  - $index$ を $prev[2] = 1$ に更新する
    - $index = 1$
- $lis = [7, 6, 5]$ , $index = 1$
  - $A[1] = 4$ を $lis$ に追加する
    - $lis = [7, 6, 5, 4]$
  - $index$ を $prev[1] = 0$ に更新する
    - $index = 0$
- $lis = [7, 6, 5, 4]$ , $index = 0$
  - $A[0] = 3$ を $lis$ に追加する
    - $lis = [7, 6, 5, 4, 3]$
  - $index$ を $prev[0] = -1$ に更新する
    - $index = -1$
- $lis = [7, 6, 5, 4, 3]$ を逆順にした $[3, 4, 5, 6, 7]$ がLISの内容である。

##### コード

```clojure
(defn lis
  "Finds the longest increasing subsequence (LIS) in a vector `A`."
  [A]
  (let [n (count A)
        L []             ;; LISを追跡するためのベクタ
        prev (vec (repeat n -1)) ;; LISを復元するために各要素の直前要素を記録
        index-in-lis []]  ;; LISに含まれる各要素のインデックス
    (loop [i 0 L L index-in-lis index-in-lis prev prev]
      (if (< i n)
        (let [a (nth A i)
              pos (bisect-left L a)]  ;; L における a の挿入位置を見つける
          ;; L[pos] に新しい値を挿入または置き換える
          (let [L-updated (if (< pos (count L))
                            (assoc L pos a)  ;; L[pos] を置き換える
                            (conj L a))      ;; L の末尾に新しい要素を追加
                index-in-lis-updated (if (< pos (count index-in-lis))
                                       (assoc index-in-lis pos i)
                                       (conj index-in-lis i))
                prev-updated (if (> pos 0)
                               (assoc prev i (nth index-in-lis (dec pos)))
                               prev)]
            (recur (inc i) L-updated index-in-lis-updated prev-updated)))
        ;; LIS を復元するために L の最後の要素から始める
        (loop [index (last index-in-lis)
               lis []]
          (if (= index -1)
            (reverse lis)  ;; LIS を返す（逆順に構築されているので反転する）
            (recur (nth prev index) (conj lis (nth A index)))))))))

(lis [3 4 5 1 6 3 2 7])
;; => (3 4 5 6 7)
```

##### LISの内容が求められる理由

- `prev` による復元
  - `prev` の役割: `prev` 配列は、LISの各要素がどの要素に続いているかを記録している。これにより、LISの最終的な要素から逆順に辿ることで、LISの内容自体を復元できる。
  - インデックスの追跡: `index-in-lis` は、LISのどの要素がどのインデックスに対応しているかを追跡し、LISの復元の際に使用する。

##### 計算量

LISの復元: $O(N)$
