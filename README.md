## 리팩토링

### 1주차

TODO : csv 파일을 읽어서 특정 컬럼명을 group by 하여 출력하는 프로그램이다.

* args[0] : resources에 보관된 csv 파일명
* args[1] : 카운트 할 컬럼명

---

### 2주차

TODO : arguments 받는 방식을 바꿔주세요.

* --file-path {filePath} 또는 -f {filePath}
* --column-name {columnName} 또는 -c {columnName}

TODO : charset이 ms949인 것도 들어올 수 있게 하기

---

### 3주차

TODO : -c를 여러개 줄 수 있도록 해주세요. (순서중요)

### 4주차

-f file.csv -c column1 -v value1 -c column2 -v value2
column1 이 숫자 컬럼인 경우 value1 값보다 크고 column2 가 숫자 컬럼인 경우 value2 값보다 큰 데이터에 대하여 그룹 카운트


-f file.csv -c column1 -c column2 -v value2
column2 가 숫자 컬럼인 경우 value2 값보다 큰 데이터에 대하여 그룹 카운트



---

### 참고자료

(java stream)

- https://recordsoflife.tistory.com/55
- https://cornswrold.tistory.com/547

(CharsetDectector)

- https://www.tabnine.com/code/java/methods/com.ibm.icu.text.CharsetDetector/detect

(commons-cli)

- https://junho85.pe.kr/432
- https://118k.tistory.com/779
- https://stackoverflow.com/questions/367706/how-do-i-parse-command-line-arguments-in-java

---
