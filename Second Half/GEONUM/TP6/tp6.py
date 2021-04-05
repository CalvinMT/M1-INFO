#-------------------------------------------------
#
#  TP6 : Bezier surfaces
#  http://tiborstanko.sk/teaching/geo-num-2017/tp6.html
#  [17-Mar-2017]
#
#-------------------------------------------------
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
#-------------------------------------------------

import sys, os
import numpy as np
from OpenGL.GL import *
from OpenGL.GLUT import *
from viewer import Viewer

TP = os.path.dirname(os.path.realpath(__file__)) + "/"
DATADIR = TP+"data/"


#-------------------------------------------------
# READBEZIERMESH()
# Read Bezier control net from a BPT file.
#
# Input
#    filename    :  file to be read
#
# Output
#    Mx, My, Mz  :  three matrices, one for each coordinate of the control mesh
#
def ReadBezierMesh( datafile ) :
    line = datafile.readline()
    deg = np.fromstring(line,sep=' ',dtype=int)
    M = np.fromfile(datafile,count=3*(deg[0]+1)*(deg[1]+1),sep=' ',dtype=float)
    M = M.reshape(-1,3).transpose().reshape( 3, deg[0]+1, deg[1]+1 )
    return M[0,:,:], M[1,:,:], M[2,:,:]


#-------------------------------------------------
# DECASTELJAU( ... )
# Compute point on a Bezier surface using the De Casteljau algorithm.
#
# Input
#    M        :  m x n matrix, control mesh (one coordinate)
#    u, v     :  parameters
#
# Output      :  one coordinate of the surface point S(u,v)
#
def DeCasteljau(M, k, l, i, j, u, v):
    
    # De Casteljau algorithm for surfaces.
    if k == 0 and l == 0:
        return M[i][j]
    elif l == 0:
        return (1 - u) * DeCasteljau(M, k - 1, l, i, j, u, v) + u * DeCasteljau(M, k - 1, l, i + 1, j, u, v)
    else:
        return (1 - v) * DeCasteljau(M, k, l - 1, i, j, u, v) + v * DeCasteljau(M, k, l - 1, i, j + 1, u, v)


#-------------------------------------------------
# BEZIERSURF( ... )
# Compute Bezier surface points.
#
# Input
#    M        :  m x n matrix, control mesh (one coordinate)
#    density  :  sampling density
#
# Output
#    S        :  density x density matrix, surface points (one coordinate)
#
# Note :
# This function is later called three times,
# for each 3d coordinate individually.
#

def BezierSurf(M, density):
    
    # surface degrees
    m, n = M.shape - np.array([1, 1])
    
    # init surface points
    S = np.zeros([density, density])

    # Fill surface points.
    u = np.linspace(0.0, 1.0, num=density)
    v = np.linspace(0.0, 1.0, num=density)
    for i in range(density):
        for j in range(density):
            S[i][j] = DeCasteljau(M, m, n, 0, 0, u[i], v[j])
    
    return S


#-------------------------------------------------
if __name__ == "__main__":
    
    # arg 1 : data name
    if len(sys.argv) > 1:
        dataname = sys.argv[1]
    else :
        dataname = "simple"

    # arg 2 : sampling density
    if len(sys.argv) > 2:
        density = int(sys.argv[2])
    else :
        density = 10

    color = [0.5, 0.6, 0.5]

    # filename
    filename = DATADIR + dataname + ".bpt"
    
    # check if valid datafile
    if not os.path.isfile(filename):
        print " error   :  invalid dataname '" + dataname + "'"
        print " usage   :  tp6.py  [simple,wave,sphere,heart,heart_inv,teapot,teacup,teacup_inv,teaspoon]  [density=10]"
        print " example :  python tp6.py wave 20"
        
    else :
        # open the datafile
        datafile = open(filename, 'r');
        
        # get first line = number of patches
        numpatch = np.fromstring(datafile.readline(), sep=' ', dtype=int)[0]
        
        # init Viewer
        viewer = Viewer("TP6 : Bezier surfaces [" + dataname + "]", [1200, 800])

        # read and compute each patch
        for p in range(numpatch) :
            
            # print patch id
            print " patch", p + 1, "/", numpatch
            
            # read Bezier control points
            Mx, My, Mz = ReadBezierMesh(datafile)
            
            # show construction polygones
            #viewer.add_patch(Mx, My, Mz, wireframe=True, color=color)
            
            # compute surface points
            Sx = BezierSurf(Mx, density)
            Sy = BezierSurf(My, density)
            Sz = BezierSurf(Mz, density)
            
            # add patch to the Viewer
            viewer.add_patch(Sx, Sy, Sz, wireframe=False, color=color)

        # print final message
        print " done."
        
        # display the viewer
        viewer.render()
