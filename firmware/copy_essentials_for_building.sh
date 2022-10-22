echo
echo "========================================================================================================================"
echo " * * * CAREFUL! Do you know what this program is for?! * * *"
echo " You should not run this becuase it will delete the toolchain provided in this repository and then"
echo " this project won't build anymore. So, therefore, don't run this!"
echo
echo " If you know what you are doing and want to run this then comment out the exit command in this script and enjoy"
echo " living an uncluttered life with just the bare essential."
echo "========================================================================================================================"
echo
exit 0 # Comment this out if you want to run this script
echo "* * * CAREFUL! About to delete the build_essential directory. Make sure you don't have unsaved source files there * * *"
echo "Press CTRL-C now if you are in doubt"
sleep 10
rm -rf toolchain_essentials
make clean
make
rm -rf ccc.sh
find ./toolchain_and_libraries -type f -atime -2m | awk '{printf("gcp --parents %s toolchain_essentials/\n", $0)}' > ./ccc.sh
chmod u+x ccc.sh
mkdir -p toolchain_essentials
mkdir -p bin_file_releases
./ccc.sh
rm -rf ccc.sh
echo "The required files for building are now located in the toolchain_essentials directory"
