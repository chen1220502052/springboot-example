#!/bin/sh

OP_DIR=/timo/op/script
WEB_HOME=/timo/timo-afterservice
SOURCE_HOME=/timo/source/timo-afterservice
WEB_BAK_HOME=/timo/op/backup/
tarball=/timo/source/timo-afterservice/target/timo-afterservice.jar

mkdir -p ${WEB_HOME}

echo "===============git update============="
cd ${SOURCE_HOME}
git pull

echo "===============maven build============="
mvn clean package -Dmaven.test.skip=true -U

if [ ! -f ${tarball} ]; then
        echo "maven build failed, will exit with 1."
        exit 1
fi

function backup_web(){
    echo "back up web started."
    mkdir -p $WEB_BAK_HOME
    web_home_entry=`ls -l $WEB_HOME | wc -l`
    if [ ${web_home_entry} -ne 1 ]; then
        cur_time=`date +%Y%m%d%H%M%S`
        bak_name=${WEB_BAK_HOME}"/timo-afterservice".${cur_time}."bak.tar.gz"
        cd $WEB_HOME && tar czf $bak_name *
        if [ $? -eq 0 ]; then
            echo "back up web completed."
        else
            echo "back up web failed, will exit with 1."
            exit 1
        fi
    else
        echo "===web home directory is empty, no need to backup==="
    fi
}

sh ${OP_DIR}/stop-web.sh
echo "===============web stopped============="
rm -rf ${WEB_HOME}/* && cp ${tarball} ${WEB_HOME}
sh ${OP_DIR}/start-web.sh
echo "===============web started============="
