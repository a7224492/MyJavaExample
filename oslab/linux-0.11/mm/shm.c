#include <string.h>
#include <linux/mm.h>
#include <errno.h>
#include <linux/sched.h>
#include <linux/kernel.h>

#define MAX_SHARE_MEMORY_SIZE 100

extern struct task_struct *current;

struct ShareMemory {
    int key;
    int shmid;
    unsigned long virutalAddress, page;
    long pid;
};

static struct ShareMemory shareMemoryArr[MAX_SHARE_MEMORY_SIZE];
static int init = 0;
static int shareNum = 0;

static int get_shmid(int key) {
    int i;

    for (i = 0; i < MAX_SHARE_MEMORY_SIZE; ++i) {
        if (shareMemoryArr[i].shmid != -1 && shareMemoryArr[i].key == key) {
            return i;
        }
    }

    return -1;
}

static int getFreeShm() {
    int i = 0;

    for (i = 0; i < MAX_SHARE_MEMORY_SIZE; ++i) {
        if (shareMemoryArr[i].shmid == -1) {
            return i;
        }
    }

    return -1;
}

static void init_key_arr_if_not() {
    int i;

    if (!init) {
        printk("Start init share memory\n");
        for (i = 0; i < MAX_SHARE_MEMORY_SIZE; ++i) {
            shareMemoryArr[i].shmid = -1;
        }

        init = 1;
    }
}

int sys_shmget(int key, unsigned int size) {
    int shmIndex, availableShmIndex;
    unsigned long page, dataBase, linearAddress, virtualAddress;

    init_key_arr_if_not();

    if (size > PAGE_SIZE) {
        printk("sys_shmget: size > PAGE_SIZE\n");
        return -EINVAL;
    }

    shmIndex = get_shmid(key);
    if (shmIndex != -1) {
        if (shareMemoryArr[shmIndex].pid == current->pid) {
            /* exist shmid */
            printk("sys_shmget: exist key\n");
            return shareMemoryArr[shmIndex].shmid;
        } else {
            page = shareMemoryArr[shmIndex].page;
            availableShmIndex = shmIndex;
        }
    } else {
        /* get free physical page */
        page = get_free_page();
        if (page == 0) {
            printk("sys_shmget: no free page\n");
            return -ENOMEM;
        }

        /* get available share memory data */
        availableShmIndex = getFreeShm();
        if (availableShmIndex == -1) {
            return -ENOMEM;
        }
    }

    /* get available linear address */
    dataBase = get_base(current->ldt[2]);
    virtualAddress = current->end_data + current->shareMemoryNum * PAGE_SIZE;
    linearAddress = dataBase + virtualAddress;
    printk("dataBase is 0x%08x, linearAddress is 0x%08x\n", dataBase, linearAddress);

    /* map linear address to physical page */
    if (!put_page(page, linearAddress)) {
        printk("can't put page");
        return -ENOMEM;
    }

    ++current->shareMemoryNum;
    printk("available shm index is %d, key is %d, virtualAddress is 0x%08x\n, shareNum = %d\n", availableShmIndex, key, virtualAddress, current->shareMemoryNum);
    shareMemoryArr[availableShmIndex].shmid = availableShmIndex;
    shareMemoryArr[availableShmIndex].key = key;
    shareMemoryArr[availableShmIndex].virutalAddress = virtualAddress;
    shareMemoryArr[availableShmIndex].page = page;
    shareMemoryArr[availableShmIndex].pid = current->pid;

    return availableShmIndex;
}

void *sys_shmat(int shmid) {
    int i;

    init_key_arr_if_not();

    if (shmid < 0 || shmid >= MAX_SHARE_MEMORY_SIZE) {
        return (void *)-EINVAL;
    }

    for (i = 0; i < MAX_SHARE_MEMORY_SIZE; ++i) {
        if (shareMemoryArr[i].shmid == shmid) {
            break;
        }
    }

    if (i == MAX_SHARE_MEMORY_SIZE) {
        return (void *)-EINVAL;
    }

    printk("Get share memory address is 0x%08x\n", shareMemoryArr[i].virutalAddress);
    return (void *)shareMemoryArr[i].virutalAddress;
}