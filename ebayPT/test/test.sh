#!/bin/bash

rm -f temp/*.temp

for jarF in *.jar; do

	echo "-------------------------------------------------------------"
	echo "Testing program: $jarF"
	echo "[using diff -y <result> <expected>]"
	echo "-------------------------------------------------------------"

	for f in tests/*in*.txt; do 

	echo "Processing $f file..";
	java -jar "$jarF" < "$f" > "${f/"in"/"out"}.temp"

	diff -y --suppress-common-lines "${f/"in"/"out"}.temp" "${f/"in"/"out"}"

	done

done