// TCP echo客户端程序
#include "headerFiles.h"

const char *SERVER_IP = "127.0.0.1";
const int SERVER_PORT = 1314;
const int BUFFER_SIZE = 256;

int main(void) {
    printf("-----------------------------------------------\n");
    printf("客户端TCP,IP:%s,PORT:%d.\n", SERVER_IP, SERVER_PORT);

    //变量的声明和定义
    int sd;				//套接字描述符
    int n;				//已接收的字节数
    int bytesToRecv;			//接收的总字节数
    char sendBuffer[BUFFER_SIZE];	//发送缓存
    char recvBuffer[BUFFER_SIZE];	//接收缓存
    char* movePtr;			//指向缓存的指针
    struct sockaddr_in serverAddr;	//套接字地址

    //创建套接字
    sd = socket(PF_INET, SOCK_STREAM, 0);
    printf("socket描述符:%d.\n", sd);

    //建立服务器套接字地址
    memset(&serverAddr, 0, sizeof(serverAddr));
    serverAddr.sin_family = AF_INET;
    inet_pton(AF_INET, SERVER_IP, &serverAddr.sin_addr);
    serverAddr.sin_port = htons(SERVER_PORT);

    //连接
    connect(sd,(struct sockaddr*)&serverAddr, sizeof(serverAddr)); 

    //发送和接收数据报
    printf("输入数据：\n");
    fgets(sendBuffer, BUFFER_SIZE, stdin);
    printf("发送数据成功:%s\n", sendBuffer);
    send(sd, sendBuffer, strlen(sendBuffer), 0);
    bytesToRecv = strlen(sendBuffer);
    movePtr = recvBuffer;
    while((n=recv(sd, movePtr, bytesToRecv, 0))>0) {
        //此处while可以正常结束是因为服务端关闭了连接
        movePtr = movePtr + n;
        bytesToRecv = bytesToRecv -n;
    }
    recvBuffer[strlen(recvBuffer)] = 0;
    printf("接收数据成功:");
    fputs(recvBuffer, stdout);
    printf("\n");
    //关闭和退出
    close(sd);
    exit(0);
}
//echo客户程序结束
