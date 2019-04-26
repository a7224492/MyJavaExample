//
// Created by jiangzhen on 2019/4/23.
//
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <semaphore.h>

int main(int argc, char **argv)
{
//    int fd, rfd;
//    char buff[40];
//
//    fd = open("file_test", O_RDWR | O_CREAT | O_TRUNC);
//    rfd = open("file_test", O_RDONLY);
//    write(fd, "jiangzhen\n", 10);
//    write(fd, "chuangwang\n", 11);
//
//    memset(buff, 0, 40);
//    read(rfd, buff, 40);
//    printf("%s\n", buff);

//    char buff[10];
//    int result;
//
//    strcpy(buff, "12");
//    sscanf(buff, "%d", &result);
//    printf("%d\n", result);

    sem_t *sem = sem_open("test", O_CREAT);
    sem_wait(sem);
    printf("Hello\n");
    sem_post(sem);

    return 0;
}