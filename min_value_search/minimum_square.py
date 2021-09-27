# find minimum size square includes all squares
# 명함의 가로세로를 긴쪽 작은쪽을 각각 두 개의 list에 넣고
# 두 list의 최대값을 찾으면 된다.
def solution(sizes):    

    max1 = list() # 가로세로 명함 한 개에서 긴쪽 길이 저장
    max2 = list() # 가로세로 명함 한 개에서 짧은 쪽 길이 저장

    for arr1 in sizes:
        maxRow = 0
        if arr1[0] < arr1[1]:
            max1.append(arr1[0])
            max2.append(arr1[1])
        else:
            max2.append(arr1[0])
            max1.append(arr1[1])

    max1.sort()
    max2.sort()

    return max1[len(max1)-1] * max2[len(max2)-1]

  
if __name__ == "__main__":
  if 4000 == solution([[60, 50], [30, 70], [60, 30], [80, 40]]):
    print(True)
  if 120 == solution([[10, 7], [12, 3], [8, 15], [14, 7], [5, 15]]):
    print(True)
  if 133 == solution([[14, 4], [19, 6], [6, 16], [18, 7], [7, 11]]):
    print(True)
