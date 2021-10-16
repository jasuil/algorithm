'''
tetris engine
 n*n square, remove number 1 block
'''
def clear(matrix):

    matrix_length = len(matrix)
    # the last index, where block has stopped
    last_index = [2, 1]
    search_queue = deque()
    search_queue.append(last_index)
    search_point = set()
    search_point.add(str(last_index[0]) + ',' + str(last_index[1]))
    block_type = 1

    while len(search_queue) > 0:
        point = search_queue.pop()
        # down
        if point[0]+1 < matrix_length and matrix[point[0]+1][point[1]] == block_type \
                and str(point[0]+1) + ',' + str(point[1]) not in search_point:
            search_queue.append([point[0]+1, point[1]])
            search_point.add(str(point[0]+1) + ',' + str(point[1]))
        # up
        if point[0]-1 > -1 and matrix[point[0]-1][point[1]] == block_type \
                and str(point[0]-1) + ',' + str(point[1]) not in search_point:
            search_queue.append([point[0]-1, point[1]])
            search_point.add(str(point[0]-1) + ',' + str(point[1]))
        # left
        if point[1]-1 > -1 and matrix[point[0]][point[1]-1] == block_type \
                and str(point[0]) + ',' + str(point[1]-1) not in search_point:
            search_queue.append([point[0], point[1]-1])
            search_point.add(str(point[0]) + ',' + str(point[1]-1))
        # right
        if point[1]+1 < matrix_length and matrix[point[0]][point[1]+1] == block_type \
                and str(point[0]) + ',' + str(point[1]-1) not in search_point:
            search_queue.append([point[0], point[1]+1])
            search_point.add(str(point[0]) + ',' + str(point[1]+1))
    if len(search_point) > 2:
        for p in search_point:
            point = p.split(',')
            matrix[int(point[0])][int(point[1])] = 0
        rearrange(matrix, search_point)

    return matrix


def rearrange(matrix, zero_points):
    for p in zero_points:
        point = p.split(',')
        for i in range(int(point[0])-1 if int(point[0]) > 0 else 0, -1, -1):
            matrix[i+1][int(point[1])] = matrix[i][int(point[1])]


if __name__ == "__main__":
    matrix = [[0, 0, 0, 0, 0], [0, 0, 3, 0, 0], [0, 1, 1, 0, 0], [2, 1, 2, 0, 0], [1, 1, 4, 3, 0]]

    for m1 in matrix:
        line = ''
        for m2 in m1:
            line += str(m2)
        print(line)

    matrix = clear(matrix)

    print('------- 1 block clear ---------')
    for m1 in matrix:
        line = ''
        for m2 in m1:
            line += str(m2)
        print(line)
