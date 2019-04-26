#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <string.h>
#include <wait.h>

#define DEBUG

#define N 10
#define FN 3
#define S "/jz"

char buff[10];

void updateBuff(int buffSizeFd, int num)
{
    char buf[8], tmp[1];
    int readNum, buffSize;
    int i;

    i = lseek(buffSizeFd, 0, SEEK_CUR);
    if (i <= 0) {
        sprintf(buf, "%d\n", num);
#ifdef DEBUG
        printf("buf=%s\nbuf_strlen=%d\n", buf, strlen(buf));
#endif
        write(buffSizeFd, buf, strlen(buf));
    } else {
        lseek(buffSizeFd, 0, SEEK_SET);

        i = 0;
        read(buffSizeFd, tmp, 1);
        while (tmp[0] != '\n') {
            buf[i++] = tmp[0];
#ifdef DEBUG
            printf("inwhile, buf=%s\n", buf);
            read(buffSizeFd, tmp, 1);
#endif
        }
        buf[i] = '\n';

        sscanf(buf, "%d", &buffSize);
        buffSize += num;
#ifdef DEBUG
        printf("buf=%s\nbuffSize=%d\n", buf, buffSize);
#endif
        sprintf(buf, "%d\n", buffSize);
#ifdef DEBUG
        printf("buf=%s\n", buf);
#endif

        lseek(buffSizeFd, 0, SEEK_SET);
        write(buffSizeFd, buf, strlen(buf));
    }
}

void producer(int writeFd, int readFd, int buffSizeFd)
{
    int i;

    for (i = 0; i < 40; ++i) {
#ifdef DEBUG
        printf("write\n");
#endif
        updateBuff(buffSizeFd, 1);
    }
}

void consumer(int writeFd, int readFd, int buffSizeFd)
{
    int i;

    for (i = 0; i < 20; ++i) {
#ifdef DEBUG
        printf("read\n");
#endif
        updateBuff(buffSizeFd, -1);
    }
}

int main() {
    int writeFd, readFd, buffSizeFd;
    int i, j, pid;

    memset(buff, 0, 10);

    writeFd = open("/home/jz/buff_file", O_WRONLY|O_CREAT|O_TRUNC);
    readFd = open("/home/jz/buff_file", O_RDONLY);
    buffSizeFd = open("/home/jz/buff_size", O_RDWR|O_CREAT|O_TRUNC);

    for (i = 0; i < FN; ++i) {
        pid = fork();
        if (pid > 0) {
            continue;
        }

        if (pid == 0) {
            if (i == 0) {
                producer(writeFd, readFd, buffSizeFd);
                return 0;
            } else {
                consumer(writeFd, readFd, buffSizeFd);
                return 0;
            }
        }
    }

    for (i = 0; i < FN; ++i) {
        wait(NULL);
    }

    return 0;
}
