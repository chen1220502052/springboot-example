SERVER_PID=`ps -aux | grep timo-afterservice| grep java | awk '{print$2}'`
echo "afterservice pid is ${SERVER_PID}"
if [ -n "$SERVER_PID" ]
then
  kill $SERVER_PID
  sleep 3s
  CHECK_PID=`ps -aux | grep timo-afterservice| grep java | awk '{print$2}'`
  while [ -n "$CHECK_PID" ]
  do
    CHECK_PID=`ps -aux | grep timo-afterservice| grep java | awk '{print$2}'`
    sleep 1s
    echo "$CHECK_PID is stopping, please waiting..."
  done
  echo "$SERVER_PID is killed!"
fi
