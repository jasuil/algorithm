"""
출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
copyright by grepp, Inc. https://www.grepp.co/

korean question: 돌다리 n개가 있고 각각의 돌다리는 k명의 사람들만 밟고 갈 수 있다.
사람 1명이 밟을때마다 1씩 돌다리의 허용개수가 줄어드는 것이다.
사람들은 돌다리를 하나씩 밟고 가야 한다. 단, 돌다리 허용개수가 0인 경우는 밟지 않고 넘어갈 수 있는데
j개 미만으로만 허용한다고 하자.

예시) 돌다리 배열, 넘어갈 수 없는 개수 = [2,4,5,4,3,3,2,5,7], 3
첫 번째 사람이 건너간 후 [1,3,4,3,2,2,1,4,6]
두 번째 사람이 건너간 후 [0,2,3,2,1,1,0,3,5]
세 번째 사람이 건너간 후 [0,1,2,1,0,0,0,2,4]
이제, 네 번째 사람이 건너가고 싶지만 밟지 않고 가는 최대 개수는 3-1 이므로 2
따라서 3명 만이 지나갈 수 있다.
"""


def solution(stones, k):
    count = len(stones)
    min_stone = min(stones)
    max_stone = max(stones)
    search_point = (min_stone + max_stone) // 2
    search_point_list = set()

    # count answer with using bisect
    while True:
        continue_zero = 0
        continue_zero_over_k = 0

        for i in range(count):
            if stones[i] <= search_point:
                continue_zero += 1
                if k <= continue_zero:
                    continue_zero_over_k = continue_zero
                    break
            else:
                continue_zero = 0

        if k <= continue_zero_over_k:
            max_stone = search_point
            search_point = (max_stone + min_stone) // 2
            if search_point in search_point_list:
                break
            search_point_list.add(search_point)
        else:
            min_stone = search_point + 1
            search_point = (max_stone + min_stone) // 2


    return search_point


if __name__ == '__main__':
    t = solution([2, 4, 5, 3, 2, 1, 4, 2, 5, 1],	3)

    print("minimum people is " + t)
