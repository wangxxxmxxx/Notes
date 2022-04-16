// TCP echo服务器程序
#include "headerFiles.h"

const int SERVER_PORT = 1314;
const int BUFFER_SIZE = 256;

int main(void) {
    printf("-----------------------------------------------\n");
    printf("服务端TCP,PORT:%d.\n", SERVER_PORT);

    //变量的声明和定义
    int listensd;			//监听套接字描述符
    int connectsd;			//连接套接字描述符
    int n;				//每一次接收的字节数
    int bytesToRecv;			//接收的总字节数
    int processID;			//子进程的ID
    char buffer[BUFFER_SIZE];		//数据缓存
    char* movePtr;			//指向缓存的指针
    struct sockaddr_in serverAddr;	//服务器地址
    struct sockaddr_in clientAddr;	//客户地址
    int clAddrLen;			//客户地址长度

    //创建套接字
    listensd = socket(PF_INET, SOCK_STREAM, 0);
    printf("监听socket描述符:%d.\n", listensd);

    //将本地地址和端口绑定到套接字
    memset(&serverAddr, 0, sizeof(serverAddr));
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_addr.s_addr = htonl(INADDR_ANY);	//默认地址
    serverAddr.sin_port = htons(SERVER_PORT);
    int ret = bind(listensd, (struct sockaddr*)&serverAddr, sizeof(serverAddr));
    if(0 > ret) {
        printf("绑定失败！");
        return -1;
    }

    //监听连接请求
    listen(listensd, 5);

    //处理连接
    for(;;) {
        printf("-----------------------------------------------\n");
	connectsd = accept(listensd, (struct sockaddr*)&clientAddr, &clAddrLen);
	printf("收到连接，连接socket:%d\n", connectsd);
        processID = fork();
        if(processID == 0){ //子进程
	    close(listensd);
            bytesToRecv = BUFFER_SIZE;
            movePtr = buffer;
            //while((n=recv(connectsd, movePtr, bytesToRecv, 0))>0) { //无法结束，需要长度头等方式结束while循环
            if((n=recv(connectsd, movePtr, bytesToRecv, 0))>0) {
                movePtr = movePtr + n;
                bytesToRecv = bytesToRecv - n;
            }
            printf("子进程收到内容:%s\n", buffer);
            send(connectsd, buffer, strlen(buffer), 0);
            exit(0);//子进程在这里退出
        }
        close(connectsd);          // 回到父进程 
    }
}
//echo服务器程序结束
