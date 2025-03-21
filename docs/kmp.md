# kmp

## KMP法

KMP法は文字列探索アルゴリズムの一種で、文字列 $S$ と文字列 $T$ について、 $T$ が $S$ に含まれているか否かを、また含まれている場合はその位置を $O(|S| + |T|)$ で求めることができるアルゴリズム。

部分マッチテーブルを構築し、それを用いて文字列探索を行う。

### 部分マッチテーブル

文字列 $T$ に対する部分マッチテーブル $A$ とは、 $1 \leq i \leq |T|$ に対して、「Proper Prefix (その文字列自身を含まないPrefix) $T[0:i]$ 」 と 「Proper Prefix の Suffix」 が最大で先頭から何文字一致するかという値を持つ配列のこと。

便宜上、 $i = 0$ の場合、 Proper Prefix $T[0:0]$ は空文字列となるため、 $A[0] = -1$ とする。

$T = CAGCATCAGCAGA$ の場合:

|$i$|$0$|$1$|$2$|$3$|$4$|$5$|$6$|
|:--|:--|:--|:--|:--|:--|:--|:--|
|Proper Prefix $T[0:i]$|-|$C$|$CA$|$CAG$|$CAGC$|$CAGCA$|$CAGCAT$|
|マッチ部分|-|なし|なし|なし|$C$|$CA$|なし|
|$A[i]$|$-1$|$0$|$0$|$0$|$1$|$2$|$0$|

|$i$|$7$|$8$|$9$|$10$|
|:--|:--|:--|:--|:--|
|$T[0:i]$|$CAGCATC$|$CAGCATCA$|$CAGCATCAG$|$CAGCATCAGC$|
|マッチ部分|$C$|$CA$|$CAG$|$CAGC$|
|$A[i]$|$1$|$2$|$3$|$4$|

|$i$|$11$|$12$|$13$|
|:--|:--|:--|:--|
|$T[0:i]$|$CAGCATCAGCA$|$CAGCATCAGCAG$|$CAGCATCAGCAGA$|
|マッチ部分|$CAGCA$|$CAG$|なし|
|$A[i]$|$5$|$3$|$0$|

#### 部分マッチテーブルの構築

$A[i]$ がすでに既知であるとする。 $A[i]$ の定義より、

$T[0:A[i]] = T[i - A[i]:i]$

が成立する。

##### $T[i] = T[A[i]]$ の場合

$T[0:A[i] + 1] = T[i - A[i]:i + 1]$

が成立するため、 $A[i + 1] = A[i] + 1$ である。

![pattern-match-table-same](/resources/img/kmp/pattern-match-table-same.avif)

> $T = CAGCATCAGCAGA$ の $i = 9$ において、 $T[0:9] = CAGCATCAG$ , $A[9] = 3$ が成立する。
>
> $T[9] = C$ , $T[A[9]] = T[3] = C$ のため、 $T[9] = T[A[9]]$ より $A[10] = A[9] + 1 = 4$ である。

##### $T[i] \neq T[A[i]]$ の場合

$T[0:A[i + 1]] = T[i + 1 - A[i + 1]:i + 1]$

が成立する。ここで、 $A[i + 1] = j + 1$ とおくと、

$T[0:j + 1] = T[i - j:i + 1] \iff T[0:j] = T[i - j:i] \land T[j] = T[i]$

が成立する。

ここで、 $T[0:j] = T[i - j:i]$ に着目すると、 $A[i]$ が $j$ の最大値であるため、 $j \leq A[i]$ である。

また、 $T[j] = T[i]$ に着目すると、 $T[i] \neq T[A[i]]$ より、 $j \neq A[i]$ である。

よって、 $j < A[i]$ が成立する。

> **例**:
>
> $T = CAGCATCAGCAGA$ の $i = 11$ において、 $T[11] = G$ , $T[A[11]] = T[5] = T$ で異なり、$T[0:A[12]] = T[0:3] = CAG$ , $T[12 - A[12]:12] = T[9:12] = CAG$ より、
>
> $T[0:A[12]] = T[12 - A[12]:12]$ が成立する。
>
> ここで、 $A[12] = 2 + 1$ とおくと、
> $T[0:2] = CA$ , $T[11 - 2:11] = T[9:11] = CA$ , $T[2] = G$ , $T[11] = G$ であり、
>
> $T[0:2 + 1] = T[11 - 2:11 + 1] \iff T[0:2] = T[11 - 2:11] \land T[2] = T[11]$ が成立する。
>
>つまり、次インデックスのマッチ部分がその延長で繋げられない時、次インデックスのマッチ部分は「現在インデックスのマッチ部分のProper Prefixである」ということ。

$A[i]$ の定義より、 $T[0:A[i]] = T[i - A[i]:i]$ が成立するので、 $j < A[i]$ より、

$T[A[i] - j:A[i]] = T[i - j:i]$

が成立する。よって、

$T[0:j + 1] = T[i - j:i + 1] \iff T[0:j] = T[A[i] - j:A[i]] \land T[j] = T[i]$

が成立する。

> **例**:
> $i = 11$ において、 $T[A[i] - j:A[i]] = T[5 - 2:5] = T[3:5] = CA$ , $T[11 - 2:11] = CA$ より、 $T[5 - 2:5] = T[11 - 2:11]$ が成立する。
