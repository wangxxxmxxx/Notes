// UDP echo服务器程序
#include "headerFiles.h"

const char *SERVER_IP = "127.0.0.1";
const int SERVER_PORT = 1314;
const int BUFFER_SIZE = 256;

int main(void) {
    printf("-----------------------------------------------\n");
    printf("客户端UDP,IP:%s,PORT:%d.\n", SERVER_IP, SERVER_PORT);

    //变量的声明和定义
    int sd;		//套接字描述符
    int ns;		//发送的字节数
    int nr;		//接收的字节数
    char buffer[BUFFER_SIZE];	//数据缓存
    struct sockaddr_in serverAddr;	//套接字地址

    //创建套接字
    sd = socket(PF_INET, SOCK_DGRAM, 0);
    printf("socket描述符:%d.\n", sd);

    //建立服务器套接字地址
    memset(&serverAddr, 0, sizeof(serverAddr));
    serverAddr.sin_family = AF_INET;
    inet_pton(AF_INET, SERVER_IP, &serverAddr.sin_addr);
    serverAddr.sin_port = htons(SERVER_PORT);

    //发送和接收数据报
    printf("输入数据：\n");
    fgets(buffer, BUFFER_SIZE, stdin);
    ns = sendto(sd, buffer, strlen(buffer), 0,(struct sockaddr*)&serverAddr, sizeof(serverAddr));
    printf("发送数据成功:%s\n", buffer);
    nr = recvfrom(sd, buffer, strlen(buffer), 0, NULL, NULL);
    buffer[nr] = 0;
    printf("接收数据成功:");
    fputs(buffer, stdout);
    printf("\n");
    //关闭和退出
    close(sd);
    exit(0);
}
//echo客户程序结束
