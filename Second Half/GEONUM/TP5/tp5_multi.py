#------------------------------------------------------
#
#  TP5 : Lane-Riesenfeld algorithm
#  http://tiborstanko.sk/teaching/geo-num-2017/tp5.html
#  [10-Mar-2017]
#
#------------------------------------------------------
#
#  This file is a part of the course:
#    Geometrie numerique (spring 2017)
#    https://github.com/GeoNumTP/GeoNum2017
#    M1 Informatique
#    UFR IM2AG
#
#  Course lecturer:
#    Georges-Pierre.Bonneau at inria.fr
#
#  Practical part:
#    Tibor.Stanko at inria.fr
#
#  Student:
#    Calvin Massonnet
#
#------------------------------------------------------

import sys, os
import matplotlib.pyplot as plt
import numpy as np

TP = os.path.dirname(os.path.realpath(__file__)) + "/"
DATADIR = filename = TP + "data/"


#-------------------------------------------------
# READDATAPOINTS()
# Read datapoints from a file.
#
# Input
#    filename :  file to be read
#
# Output
#    DataPts  :  d x 2 matrix, represents a closed polygon
#
def ReadDatapoints( filename ) :
    datafile = open(filename,'r');
    p = np.fromstring(datafile.readline(),sep=' ',dtype=int)
    DataPts = np.fromfile(datafile,count=2*p,sep=' ',dtype=float)
    DataPts = DataPts.reshape(-1,2)
    return DataPts


#-------------------------------------------------
# LANERIESENFELD()
# Perform one iteration of the Lane-Riesenfeld algorithm.
#
# Input
#    X0       :  n x 2 matrix, initial polygon
#    degree   :  degree of subdivision
#
# Output
#    X1       :  2n x 2 matrix, subdivided polygon
#
#
# reference paper:
#   Lane, Riesenfeld
#   A Theoretical Development for the Computer Generation
#    and Display of Piecewise Polynomial Surfaces
#   1980. IEEE Transactions on Pattern Analysis and Machine Intelligence
#   https://doi.org/10.1109/TPAMI.1980.4766968
#
#
def LaneRiesenfeld(X0, degree) :
    
    # number of points
    n = X0.shape[0]
    
    # upsample
    X1 = np.zeros([2 * n, 2])
    
    # duplicate points
    for i in range(n * 2):
        X1[i] = X0[i / 2]
    
    # Lane-Riesenfeld algorithm
    for d in range(1, degree):
        tmp = X1.copy()
        for i in range(n * 2):
            X1[i % (n * 2)] = (1.0 / 2.0) * (tmp[i % (n * 2)] + tmp[(i + 1) % (n * 2)])
    
    return X1


#-------------------------------------------------
# FOURPOINT()
# Perform one iteration of the 4-point Lane-Riesenfeld subdivision.
# 
# Input
#    X0       :  n x 2 matrix, initial polygon
#    degree   :  degree of subdivision
#
# Output
#    X1       :  2n x 2 matrix, subdivided polygon
#
#
# reference paper:
#   Cashman et al.
#   Generalized Lane-Riesenfeld algorithms
#   2013. Computer Aided Geometric Design
#   http://dx.doi.org/10.1016/j.cagd.2013.02.001
#
#
def FourPoint(X0, degree) :
    
    # number of points
    n = X0.shape[0]
    
    # upsample
    X1 = np.zeros([2 * n, 2])
    
    # duplicate points
    for i in range(n):
        X1[i * 2] = X0[i]
        X1[i * 2 + 1] = (1.0 / 16.0) * (-1.0 * X0[(i - 1) % n] + 9.0 * X0[i % n] + 9.0 * X0[(i + 1) % n] - X0[(i + 2) % n])
    
    # 4-point LR scheme
    for d in range(1, degree):
        tmp = X1.copy()
        for i in range(n * 2):
            X1[i % (n * 2)] = (1.0 / 16.0) * (-1.0 * tmp[(i - 1) % (n * 2)] + 9.0 * tmp[i % (n * 2)] + 9.0 * tmp[(i + 1) % (n * 2)] - tmp[(i + 2) % (n * 2)])
    
    return X1


#-------------------------------------------------
# SIXPOINT()
# Perform one iteration of the 6-point Lane-Riesenfeld subdivision.
# 
# Input
#    X0       :  n x 2 matrix, initial polygon
#    degree   :  degree of subdivision
#
# Output
#    X1       :  2n x 2 matrix, subdivided polygon
#
#
# reference paper:
#   Ashraf et al.
#   A Six-Point Variant on the Lane-Riesenfeld Algorithm
#   2014. Journal of Applied Mathematics
#   http://dx.doi.org/10.1155/2014/628285
#
#
def SixPoint(X0, degree) :
    
    # number of points
    n = X0.shape[0]
    
    # upsample
    X1 = np.zeros([2 * n, 2])
    
    # duplicate points
    for i in range(n):
        X1[i * 2] = X0[i]
        X1[i * 2 + 1] = (1.0 / 256.0) * (3.0 * X0[(i - 2) % n] - 25.0 * X0[(i - 1) % n] + 150.0 * X0[i % n] + 150.0 * X0[(i + 1) % n] - 25.0 * X0[(i + 2) % n] + 3.0 * X0[(i + 3) % n])
    
    # 6-point LR scheme
    for d in range(1, degree):
        tmp = X1.copy()
        for i in range(2 * n):
            X1[i % (n * 2)] = (1.0 / 256.0) * (3.0 * tmp[(i - 2) % (n * 2)] - 25.0 * tmp[(i - 1) % (n * 2)] + 150.0 * tmp[i % (n * 2)] + 150.0 * tmp[(i + 1) % (n * 2)] - 25.0 * tmp[(i + 2) % (n * 2)] + 3.0 * tmp[(i + 3) % (n * 2)])
    
    return X1


#-------------------------------------------------
if __name__ == "__main__":
    
    #---------------------------------------------
    # arg 1 : data name 
    if len(sys.argv) > 1 :
        dataname = sys.argv[1]
    else :
        dataname = "hepta"
    
    #---------------------------------------------
    # arg 2 : scheme name
    if len(sys.argv) > 2 :
        scheme = sys.argv[2]
    else :
        scheme = "LR"
    
    # check
    if scheme == "LR" :
        schemeName = "Lane-Riesenfeld"
    elif scheme == "FP" :
        schemeName = "4-point"
    elif scheme == "SP" :
        schemeName = "6-point"
    else :
        print " error :  invalid scheme " + scheme
        print "          should be LR or FP or SP"
        sys.exit(0)
    
    #---------------------------------------------
    # arg 3 : degree of the curve
    if len(sys.argv) > 3 :
        degree = int(sys.argv[3])
    else :
        degree = [2, 3, 5, 10, 30]
    
    #---------------------------------------------
    # arg 4 : number of subdivisions
    if len(sys.argv) > 4 :
        subdivisions = int(sys.argv[4])
    else :
        subdivisions = 5
    
    # filename
    filename = DATADIR + dataname + ".data"
    
    # check if valid datafile
    if not os.path.isfile(filename) :
        print " error :  invalid dataname '" + dataname + "'"
        print " usage :  python tp5.py  [data=hepta,bone,infinity,sumsign,clover]  [scheme=LR,FP,SP]  [degree=3]  [subdivisions=5]"
        
    else :
        
        # read data
        P = ReadDatapoints(filename)
        
        # set axes with equal proportions
        plt.axis('equal')
        
        # plot the data
        plt.cla()
        
        # input polygon
        plt.fill( P[:, 0], P[:, 1], edgecolor=.33 * np.ones(3), linewidth=1, linestyle='--', fill=False)
        
        from itertools import cycle
        linecycle = cycle(['-', '--', '-.', (0, (3, 5, 1, 5, 1, 5)), ':'])
        for d in range(len(degree)):
            # init subdivided polygon
            X = P
            # iterative subdivision
            for i in range(subdivisions) :
                
                # Lane-Riesenfeld
                if scheme == "LR" :
                    X = LaneRiesenfeld(X, degree[d])
                
                # 4-point
                elif scheme == "FP" :
                    X = FourPoint(X, degree[d])
                
                # 6-point
                else :
                    X = SixPoint(X, degree[d])
            
            if scheme == "FP":
                colour = 'tab:orange'
            elif scheme == "SP":
                colour = 'tab:green'
            else:
                colour = 'tab:blue'
            # refined polygon
            plt.fill( X[:, 0], X[:, 1], edgecolor=colour, linestyle=next(linecycle), linewidth=2, fill=False )
        
        # titles
        # plot
        ptitle  = dataname + " : "
        ptitle += schemeName + ", "
        ptitle += "deg=" + str(degree) + ", "
        ptitle += "sub=" + str(subdivisions)
        plt.title(ptitle)
        # figure
        plt.gcf().canvas.set_window_title('TP5 Lane-Riesenfeld')
        
        # Save the render as png image in data/ folder
        plt.savefig(DATADIR + dataname + "_" + scheme + "_" + str(degree) + "_" + str(subdivisions) + ".png")
        
        # render
        plt.show()
