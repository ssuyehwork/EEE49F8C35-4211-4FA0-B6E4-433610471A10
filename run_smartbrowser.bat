@echo off
:: 设置 Windows 控制台编码为 UTF-8，解决日志乱码问题
chcp 65001 > nul

echo ==========================================
echo       SmartBrowser 启动器 (Windows)
echo ==========================================
echo.
echo 正在检查 Maven 环境...
call mvn -v > nul 2>&1
if %errorlevel% neq 0 (
    echo [错误] 未找到 mvn 命令。请确保 Maven 已安装并配置到 PATH 变量中。
    pause
    exit /b 1
)

echo 正在编译并运行 SmartBrowser...
echo 提示：如果是第一次运行，下载依赖可能需要一些时间。
echo.

:: 使用 UTF-8 强制参数运行
call mvn clean javafx:run -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Dcom.sun.webkit.defaultEncoding=UTF-8

if %errorlevel% neq 0 (
    echo.
    echo [错误] 程序异常退出。请检查上方的错误日志。
    pause
)
