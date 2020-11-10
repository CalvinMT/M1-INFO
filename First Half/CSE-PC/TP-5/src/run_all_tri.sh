#!/bin/sh

## ################################################################################ ##
## SCRIPT NAME
## 		run_all_tri.sh
##
## SYNOPSIS
##      run_all_tri.sh [OPTIONS]
##
## DESCRIPTION
## 		Creates vectors in the "vectors" folder and orders them using 
## 		"tri_sequentiel" and "tri_threads" programs. At the end of each of their 
## 		executions, stores the returned value (time in microseconds) in the 
## 		appropriate files and folders in the "results" folder.
##
## OPTIONS
## 		-h, --help			Show usage
## 		-i, --iterations	Number of times a program is called on the same vector 
## 		-v, --vectors		Number of elements per vector
## 		-t, --threads		Number of threads per paralleled launch
##
## EXAMPLES
## 		run_all_tri.sh -i=30 -v="10000 100000 1000000" -t="1 2 4 8"
##
## IMPLEMENTATION
## 		version:		1.0.0
## 		author:			Calvin Massonnet
##
## HISTORY
## 		dd/mm/yyyy
##
## 		08/11/2020 : Calvin Massonnet : Script creation
##
## ################################################################################ ##

PROGRAM_USAGE="usage: ${0##*/} [-h|--help] [-i|--iterations=max_iterations] [-v|--vectors=vectors] [-t|--threads=threads]"
PROGRAM_NO_OPTION="help: ${0##*/} -h"

ARG_I=false
ARG_V=false
ARG_T=false

MAX_ITERATIONS=30
VECTORS="10000 100000 1000000"
THREADS="1 2 4 8"

for arg in "$@"
do
    case $arg in
        -h|--help)
        printf "%s\n" "$PROGRAM_USAGE"
        exit 0
        ;;
        -i=*|--iterations=*)
        MAX_ITERATIONS="${arg#*=}"
        ARG_I=true
        shift
        ;;
        -v=*|--vectors=*)
        VECTORS="${arg#*=}"
        ARG_V=true
        shift
        ;;
        -t=*|--threads=*)
        THREADS="${arg#*=}"
        ARG_T=true
        shift
        ;;
        *)
        # unknown option
        ;;
    esac
done

if ! $ARG_I && ! $ARG_V && ! $ARG_T; then printf "%s\n" "$PROGRAM_NO_OPTION"; fi

if ! $ARG_I; then printf "using default max. iterations: \t%d\n" $MAX_ITERATIONS; fi
if ! $ARG_V; then printf "using default vectors: \t\t%s\n" "$VECTORS"; fi
if ! $ARG_T; then printf "using default threads: \t\t%s\n" "$THREADS"; fi

printf "preparing...\n"

VECTOR_DIR="vectors/"
RESULT_DIR="results/"
RESULT_SEQUENTIAL_DIR="${RESULT_DIR}sequential/"
RESULT_PARALLEL_DIR="${RESULT_DIR}parallel/"

mkdir -p $VECTOR_DIR $RESULT_DIR
mkdir -p $RESULT_SEQUENTIAL_DIR $RESULT_PARALLEL_DIR

RESULT_SEQUENTIAL_TIME_DIR="${RESULT_SEQUENTIAL_DIR}time/"
RESULT_SEQUENTIAL_RESOURCES_DIR="${RESULT_SEQUENTIAL_DIR}resources/"
RESULT_PARALLEL_TIME_DIR="${RESULT_PARALLEL_DIR}time/"
RESULT_PARALLEL_RESOURCES_DIR="${RESULT_PARALLEL_DIR}resources/"

mkdir -p $RESULT_SEQUENTIAL_TIME_DIR $RESULT_SEQUENTIAL_RESOURCES_DIR
mkdir -p $RESULT_PARALLEL_TIME_DIR $RESULT_PARALLEL_RESOURCES_DIR

printf "starting...\n"

for vectorSize in $VECTORS
do
    # ############################################## #
    # VECTOR CREATION
    vectorFile="${VECTOR_DIR}v_${vectorSize}.vector"
    printf "creating vector file %s...\n" $vectorFile
    ./creer_vecteur -s $vectorSize > $vectorFile

    # ############################################################################# #
    # SEQUENTIAL TIME
    resultFile="${RESULT_SEQUENTIAL_TIME_DIR}res_sequential_time_${vectorSize}.txt"
    # remove previous results
    rm -f $resultFile
    printf "sequential time ordering...\n"
    for i in $(seq 1 $MAX_ITERATIONS)
    do
        ./tri_sequentiel -q -t < $vectorFile >> $resultFile
    done

    # ####################################################################################### #
    # SEQUENTIAL RESOURCES
    resultFile="${RESULT_SEQUENTIAL_RESOURCES_DIR}res_sequential_resources_${vectorSize}.txt"
    # remove previous results
    rm -f $resultFile
    printf "sequential resources ordering...\n"
    for i in $(seq 1 $MAX_ITERATIONS)
    do
        ./tri_sequentiel -q -r < $vectorFile >> $resultFile
    done



    for nbThreads in $THREADS
    do
        # ###################################################################################### #
        # PARALLEL TIME
        resultFile="${RESULT_PARALLEL_TIME_DIR}res_parallel_${nbThreads}_time_${vectorSize}.txt"
        # remove previous results
        rm -f $resultFile
        printf "parallel %d time ordering...\n" $nbThreads
        for i in $(seq 1 $MAX_ITERATIONS)
        do
            ./tri_threads -p $nbThreads -q -t < $vectorFile >> $resultFile
        done

        # ################################################################################################ #
        # PARALLEL RESOURCES
        resultFile="${RESULT_PARALLEL_RESOURCES_DIR}res_parallel_${nbThreads}_resources_${vectorSize}.txt"
        # remove previous results
        rm -f $resultFile
        printf "parallel %d resources ordering...\n" $nbThreads
        for i in $(seq 1 $MAX_ITERATIONS)
        do
            ./tri_threads -p $nbThreads -q -r < $vectorFile >> $resultFile
        done
    done
done

printf "done\n"
