echo $1
sudo ./mount-hdc
cp $1 hdc/usr/root
sudo umount hdc
