#------------------------------------------------------
#
#  TP1 : Bezier curves, De Casteljau's algorithm
#  http://tiborstanko.sk/teaching/geo-num-2017/tp1.html
#  [03-Feb-2017]
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
from copy import deepcopy

TP = os.path.dirname(os.path.realpath(__file__)) + "/"
DATADIR = filename = TP+"data/"


#-------------------------------------------------
# READPOLYGON()
# Read Bezier control points from a file.
#
def ReadPolygon( filename ) :
    datafile = open(filename,'r');
    l = datafile.readline()
    degree = np.fromstring(l,sep=' ',dtype=int)
    BezierPts = np.fromfile(datafile,count=2*(degree+1),sep=' ',dtype=float)
    BezierPts = BezierPts.reshape(-1,2)
    return BezierPts


#-------------------------------------------------
# DECASTELJAU( ... )
# Perform the De Casteljau algorithm.
#
# Input
#    BezierPts :  (degree+1) x 2 matrix of Bezier control points
#    k         :  upper index of the computed point (depth of the algorithm)
#    i         :  lower index of the computed point
#    t         :  curve parameter in [0.0,1.0]
#
# Output
#    point b_i^k from the De Casteljau algorithm.
#
def DeCasteljau( BezierPts, k, i, t ) :
    DC = deepcopy(BezierPts)
    for c in range(1, k + 1):
        for p in range(len(BezierPts) - c):
            DC[p][0] = ((1 - t) * DC[p][0]) + (t * DC[p + 1][0])
            DC[p][1] = ((1 - t) * DC[p][1]) + (t * DC[p + 1][1])
    return DC[i]


#-------------------------------------------------
# BEZIERCURVE( ... )
# Compute points on the Bezier curve.
#
# Input
#    BezierPts :  (degree+1) x 2 matrix of Bezier control points
#    N         :  number of curve samples
#    
# Output
#    CurvePts  :  N x 2 matrix of curvepoints
#
def BezierCurve( BezierPts, N ) :
    
    # degree of the curve (one less than the number of control points)
    degree = BezierPts.shape[0]-1
    
    # initialize curvepoints as zeros
    CurvePts = np.zeros([N,2])
    
    # Compute N curve points for t varying uniformly in [0.0,1.0]
    #
    # hint1:
    # to generate the uniform sampling of the interval [0.0,1.0] with N elements, use:
    # >> samples = np.linspace(0.0,1.0,num=N)
    #
    samples = np.linspace(0.0, 1.0, num=N)
    
    #
    # hint2:
    # to access and change i-th row of the matrix CurvePts, use:
    # >> CurvePts[i,:]
    #
    # hint3:
    # the actual point b_0^degree on the curve is computed by calling DeCasteljau
    # with arguments k=degree, i=0.
    #
    for i in range(N):
        CurvePts[i, :] = DeCasteljau(BezierPts, degree, 0, samples[i])
    
    return CurvePts


#-------------------------------------------------
if __name__ == "__main__":
    
    # arg 1 : data name 
    if len(sys.argv) > 1 :
        dataname = sys.argv[1]
    else :
        dataname = "simple" # simple, infinity, spiral

    # arg 2 : sampling density
    if len(sys.argv) > 2 :
        density = int(sys.argv[2])
    else :
        density = 10

    # filename
    filename = DATADIR + dataname + ".bcv"
    
    # check if valid datafile
    if not os.path.isfile(filename) :
        print "error:  invalid dataname '" + dataname + "'"
        print "usage:  python tp1.py  [simple,infinity,spiral]  [sampling_density]"
        
    else :    
        # read control points
        BezierPts = ReadPolygon(filename)

        # compute curve points
        CurvePts = BezierCurve(BezierPts,density)

        # plot
        fig = plt.gcf()
        fig.canvas.set_window_title('TP1 Bezier curves')
        plt.title(dataname+', '+str(density)+" pts")
        
        # set axes with equal proportions
        plt.axis('equal')
        
        # plot the control polygon
        plt.plot( BezierPts[:,0], BezierPts[:,1], 'bo-' )
        
        # plot the curve
        plt.plot( CurvePts[:,0], CurvePts[:,1], 'r-' )
        
        # save the render as png image in the data/ dir
        plt.savefig( DATADIR + dataname + "_" + str(density) + ".png" )
        
        # Compute intermediate polygons b_i^k for k=1,...,degree-1 and i=0,...,degree-k
        IP = np.zeros([len(BezierPts) - 1, len(BezierPts) - 1, 2])
        for k in range(1, len(BezierPts) - 1):
            for b in range(len(BezierPts) - 1):
                IP[k - 1][b] = DeCasteljau(BezierPts, k, b, 0.5)
        
        # Add plt.plot commands to plot the intermediate polygons
        for k in range(0, len(BezierPts) - 2):
            plt.plot(IP[k][:, 0], IP[k][:, 1], 'go-')
        
        # save the render as png image in the data/ dir
        plt.savefig( DATADIR + dataname + "_" + str(density) + "_intermediate" + ".png" )
        
        plt.show()
