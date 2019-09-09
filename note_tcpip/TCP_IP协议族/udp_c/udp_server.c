// UDP echo服务器程序
#include "headerFiles.h"

const int SERVER_PORT = 1314;
const int BUFFER_SIZE = 256;

int main(void) {
    printf("-----------------------------------------------\n");
    printf("服务端UDP,PORT:%d.\n", SERVER_PORT);

    //变量的声明和定义
    int sd;		//套接字描述符
    int nr;		//接收的字节数
    char buffer[BUFFER_SIZE];	//数据缓存
    struct sockaddr_in serverAddr;	//服务器地址
    struct sockaddr_in clientAddr;	//客户地址
    int clAddrLen;			//客户地址长度

    //创建套接字
    sd = socket(PF_INET, SOCK_DGRAM, 0);
    printf("socket描述符:%d.\n");

    //将本地地址和端口绑定到套接字
    memset(&serverAddr, 0, sizeof(serverAddr));
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_addr.s_addr = htonl(INADDR_ANY);	//默认地址
    serverAddr.sin_port = htons(SERVER_PORT);
    int ret = bind(sd, (struct sockaddr*)&serverAddr, sizeof(serverAddr));
    if(0 > ret) {
        printf("绑定失败！");
        return -1;
    }

    //接收并回送数据报
    for(;;) {
        printf("-----------------------------------------------\n");
        nr = recvfrom(sd, buffer, BUFFER_SIZE, 0, (struct sockaddr*)&clientAddr, &clAddrLen);
        printf("收到报文,来自%s:%d.\n", inet_ntoa(clientAddr.sin_addr),ntohs(clientAddr.sin_port));
        printf("收到内容:%s\n", buffer);
        sendto(sd, buffer, nr, 0, (struct sockaddr*)&clientAddr, sizeof(clientAddr));
        
    }
}
//echo服务器程序结束
