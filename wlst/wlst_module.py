"""
Online WLST Initialization script that sets up the runtime environment for WLST
This init script file defines all the WLST commands that can be run in online mode.
There are a few Un-documented functions which should not be used if you import this
script, the behavior of those functions might change in future releases.
@author Satya Ghattu
Copyright (c) 2004 by BEA Systems, Inc. All Rights Reserved.

WARNING: This file is part of the WLST implementation and as such may
change between versions of WLST. You should not try to reuse the logic
in this script or keep copies of this script. Doing so could cause
your WLST scripts to fail when you upgrade to a different version of
WLST.

"""

# Define Imports that will be used
import sys
import java
import jarray
import weblogic.management.scripting.WLScriptContext as wlctx
import weblogic.diagnostics.accessor.AccessorServletClient as AccessorServletClient
from jarray import array
from java import *
from weblogic import *
import weblogic.version
from java.lang import *
from javax.management import *
from weblogic.management.scripting import ScriptException
from org.python.core import PyArray
from org.python.core import PyClass
from org.python.core import PyComplex
from org.python.core import PyDictionary
from org.python.core import PyException
from org.python.core import PyFile
from org.python.core import PyFloat
from org.python.core import PyInteger
from org.python.core import PyList
from org.python.core import PyLong
from org.python.core import PyObject
from org.python.core import PyString
from weblogic.management.scripting.utils import WLSTUtil

# Define all the global variables here

theInterpreter=WLSTUtil.ensureInterpreter();
WLS_ON=WLSTUtil.ensureWLCtx(theInterpreter)
nmService=WLS_ON.getNodeManagerService();

home=None
adminHome=None
cmo=None
CMO=None
mbs=None
cmgr=None
domainRuntimeService=None
runtimeService=None
editService=None
_editService=None
typeService=None
myps1="wls:/offline> "
sys.ps1=myps1
ncPrompt="wls:/offline> "
serverName = "";
domainName = "";
connected = "false"
lastPrompt = ""
domainType = ""
promptt = ""
hideProm = "false"
username = ""
wlsversion = weblogic.version.getVersions()
version = weblogic.version.getVersions()
isAdminServer = ""
recording = "false"
scriptMode = "false"
dcCalled = "false"
exitonerror = "true"
wlstPrompt = "true"
wlstVersion = "dev2dev version"
oldhook=sys.displayhook
true=1
false=0
LAST = None

# Un-documented
# @exclude
def init():
  'This function initializes all the global variables to their default values'
  global cmo,home,adminHome,dcCalled,lastPrompt,domainName,hideProm,domainType
  global showStack,wlsversion,mbs,connected,myps1,promptt,ncPrompt
  global serverName,cmgr, domainRuntimeService, runtimeService, editService, typeService, scriptMode, LAST
  cmo=None
  home=None
  adminHome=None
  cmgr=None
  dcCalled=None
  domainName=None
  domainType=None
  showStack=None
  wlsversion=weblogic.version.getVersions()
  version=weblogic.version.getVersions()
  mbs=None
  connected="false"
  serverName=None
  scriptMode="false"
  promptt=""
  myps1=ncPrompt
  LAST=None

"""
All Control Related Commands start here
-----------------------------------------------------------------
"""

def disconnect(force="false"):
  try:
    global connected,cmo,home,adminHome,mbs,promptt
    cmo=None
    mbs=None
    connected="false"
    adminHome=None
    home=None
    promptt=""
    myps1=ncPrompt
    WLS_ON.dc(force)
    updateGlobals()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
  WLSTUtil.setupOffline(theInterpreter)
  evaluatePrompt()

def redirect(outputFile=None, toStdOut="true"):
  try:
    WLS_ON.redirect(outputFile, toStdOut)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())  

def stopRedirect():
  try:
    WLS_ON.stopRedirect()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

"""
End of all Control Commands
---------------------------------------------------------------------
"""

"""
Start Browse Commands
-----------------------------------------------------------------------
"""
def cd(mbeanName):
  """
  This function allows a user to navigate from one MBean instance
  to another instance and viceversa
  """
  try:
    global cmo, LAST
    WLS_ON.cd(mbeanName)
    WLS_ON.print("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return cmo
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def prompt(myPrompt="xx"):
  """
  Toggles the prompt. Useful when the prompt gets longer.
  """
  try:
    global hideProm,wlstPrompt,myps1
    if myPrompt=="off":
      wlstPrompt="false"
      sys.ps1=">>>"
      myps1=">>>"
      return
    elif myPrompt=="on":
      wlstPrompt="true"
      updateGlobals()
    else:
      wlstPrompt="true"
      if hideProm=="false":
        hideProm="true"
      elif hideProm == "true":
        hideProm = "false"
      updateGlobals()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def pwd():
  global LAST
  thePr = WLS_ON.getPrompt()
  if thePr == "":
    LAST = WLS_ON.getTree()+":/"
    return WLS_ON.getTree()+":/"
  LAST = WLS_ON.getTree()+":"+thePr
  return WLS_ON.getTree()+":"+thePr

"""
End of all Browse Commands
-------------------------------------------------------------------------
"""

"""
Start All Deployment Commands
-------------------------------------------------------------------------
"""
def deploy(appName,path,targets="",stageMode=None,planPath=None,**options):
  global LAST
  try:
    LAST = WLS_ON.deploy(appName,
                  path,
                  targets,
                  stageMode,
                  planPath,
                  options)
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def redeploy(appName, planPath=None, **options):
  global LAST
  try:
    LAST = WLS_ON.redeploy(appName, planPath, options)
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def undeploy(appName,targets=None, **options):
  global LAST
  try:
    LAST = WLS_ON.undeploy(appName, targets, options)
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

"""
End of All Deployment Commands
------------------------------------------------------------------------------
"""

"""
Start of All JSR-88 Deployment Commands
------------------------------------------------------------------------------
"""
      
def loadApplication(appPath, planPath=None, createPlan='true'):
  global LAST
  'This will load the application and return the WLSTPlan object' 
  hideDisplay()
  LAST = WLS_ON.loadApplication(appPath, planPath, createPlan)
  return LAST

def loadApp(appPath, planPath=None):
  global LAST
  LAST = loadApplication(appPath, planPath)
  return LAST
  
def distributeApplication(appPath, planPath=None, targets=None, **options ):
  global LAST
  hideDisplay()
  LAST = WLS_ON.distributeApplication(appPath, planPath, targets, options)
  return LAST

def distributeApp(appPath, planPath=None, targets=None, **options ):
  return distributeApplication(appPath, planPath, targets, options)

def listApplications():
  try:
    WLS_ON.listApplications()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def startApplication(appName, **options):
  global LAST
  hideDisplay()
  LAST = WLS_ON.startApplication(appName, options)
  return LAST

def startApp(appName, **options):
  return startApplication(appName, options)

def stopApplication(appName, **options):
  global LAST
  hideDisplay()
  LAST = WLS_ON.stopApplication(appName, options)
  return LAST

def stopApp(appName, block="true"):
  return stopApplication(appName, block)

def updateApplication(appName, planPath, **options):
  global LAST
  hideDisplay()
  LAST = WLS_ON.updateApplication(appName, planPath, options)
  return LAST

def updateApp(appName, planPath, **options):
  hideDisplay()
  return updateApplication(appName, planPath, options)

def getWLDM():
  global LAST
  hideDisplay()
  LAST = WLS_ON.getWLDM()
  return LAST



"""
End of All JSR-88 Deployment Commands
------------------------------------------------------------------------------
"""

"""
Start of All Edit Commands
-----------------------------------------------------------------------------
"""

def activate(timeout=300000,
             block='true'):
  global LAST
  try:
    res = _editService.activate(timeout,
                                block)
    updateGlobals()
    hideDisplay()
    LAST = res
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def startEdit(waitTimeInMillis=0,
              timeOutInMillis=-1,
              exclusive="false"):
  global hideProm
  try:
    _editService.startEdit(waitTimeInMillis,
                          timeOutInMillis,
                          exclusive)
    evaluatePrompt()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


def save():
  global hideProm
  try:
    _editService.save()
    evaluatePrompt()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())



def undo(unactivatedChanges='false',
         defaultAnswer='from_prompt'):
  global hideProm
  try:
    op='y'
    if defaultAnswer=='from_prompt':
      op=raw_input("Sure you would like to undo your changes? (y/n)")
    else:
      op=defaultAnswer
    if op=='y':
      _editService.undo(unactivatedChanges)
    else:
      WLS_ON.println('undo is not performed')
    evaluatePrompt()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


def stopEdit(defaultAnswer='from_prompt'):
  global hideProm
  try:
    op='y'
    if defaultAnswer=='from_prompt':
      op=raw_input("Sure you would like to stop your edit session? (y/n)")
    else:
      op=defaultAnswer
    if op=='y':
      _editService.stopEdit()
      WLS_ON.println('Edit session has been stopped successfully.')
    else:
      WLS_ON.println('Your edit session is not stopped, you can continue your edits.')
    evaluatePrompt()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


def cancelEdit(defaultAnswer='from_prompt'):
  global hideProm
  try:
    op='y'
    if defaultAnswer=='from_prompt':
      op=raw_input("Sure you would like to cancel the edit session? (y/n)")
    else:
      op=defaultAnswer
    if op=='y':
      _editService.cancelEdit()
      WLS_ON.println('Edit session is cancelled successfully')
    else:
      WLS_ON.println('Edit session is not stopped, you can continue your edits.')
    evaluatePrompt()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def showChanges(onlyInMemory="false"):
  try:
    _editService.showChanges(onlyInMemory)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())      

def isRestartRequired(attributeName=None):
  global LAST
  try:
    hideDisplay()
    LAST = _editService.isRestartRequired(attributeName)
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())      

def validate():
  global LAST
  try:
    hideDisplay()
    LAST = _editService.validate()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())    

def getActivationTask():
  global hideProm, LAST
  try:
    LAST = _editService.getActivationTask()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def create(name, childMBeanType=None, baseProviderType=None):
  global LAST
  try:
    hideDisplay()
    LAST = WLS_ON.create(name, childMBeanType, baseProviderType)
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def destroy(name, childMBeanType=None):
  return delete(name, childMBeanType)

def delete(name, childMBeanType=None):
  global LAST
  try:
    hideDisplay()
    LAST = WLS_ON.delete(name,childMBeanType)
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      

def get(attrName):
  global LAST
  try:
    getObj = WLS_ON.get(attrName)
    updateGlobals()
    LAST = getObj
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def getMBean(mbeanPath=None):
  global LAST
  hideDisplay()
  LAST = WLS_ON.getMBean(mbeanPath)
  return LAST

def set(attrName, value):
  try:
    setObj = WLS_ON.set(attrName,value)
    updateGlobals()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def setEncrypted(attrName, propertyName, configFile=None, secretFile=None):
  try:
    WLS_ON.setEncrypted(attrName, propertyName, configFile, secretFile)
    updateGlobals()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def invoke(methodName, parameters, signatures):
  global LAST
  try:
    invokeObj = WLS_ON.invoke(methodName,
                              parameters,
                              signatures)
    updateGlobals()
    hideDisplay()
    LAST = invokeObj
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def encrypt(obj, domainDir=None):
  global LAST
  try:
    result = WLS_ON.encrypt(obj, domainDir)
    hideDisplay()
    LAST = result
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


"""
End of All Edit Functions
--------------------------------------------------------------------
"""

"""
Start of All Information Commands
----------------------------------------------------------------------
"""

import java.util.ArrayList
import java.util.HashMap
def _getList(children, attrType):
    if ( children == None):
        return
    # split into a list of children
    childList = children.split("\n");
    lsc_result = java.util.ArrayList()
    lsa_result = java.util.HashMap()
    lso_result = java.util.HashMap()
    result = java.util.ArrayList()
    if attrType == None:
      for child in childList:

        # get a child info into a list
        childInfo = child.rstrip().split("   ");
        length = len(childInfo);
        if (len(childInfo) == 2):
            # this will add the children 
            if String(childInfo[0]).startsWith("d"):
              result.add(childInfo[1])
        if (len(childInfo) == 3):
            # this will add the attribute names 
            result.add(childInfo[1])
      return result
      
    for child in childList:

        # get a child info into a list
        childInfo = child.rstrip().split("   ");
        length = len(childInfo);
        if (len(childInfo) == 2):
            # this will add the children 
            # make sure we are not adding any attributes
            if String(childInfo[0]).startsWith("d"):
              lsc_result.add(childInfo[1])
        if (len(childInfo) == 3):
            # this will add the attribute names 
            lsa_result.put(childInfo[1], childInfo[2])
        if (len(childInfo) == 4 or len(childInfo) == 5):
            # this will add the operation names 
            if len(childInfo) == 5:
              lso_result.put(childInfo[1], childInfo[2]+"||"+childInfo[4])
            else:
              lso_result.put(childInfo[1], childInfo[2])
    if attrType == "a":
      return lsa_result
    if attrType == "c":
      return lsc_result
    if attrType == "o":
      return lso_result
    if lsc_result.size() > 0:
      return lsc_result
    return lsa_result

def ls(attrName=None, returnMap="false", returnType=None):
  global LAST
  try:
    obj = WLS_ON.ls(attrName, returnType)
    WLS_ON.println("")
    hideDisplay()
    if returnMap == "true":
      LAST = _getList(obj, attrName)
      return LAST
    LAST = obj
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


def dumpVariables():
  try:
    WLS_ON.dumpVariables()
    WLS_ON.println('exitonerror'+WLS_ON.calculateTabSpace("exitonerror",30)+exitonerror)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


def man(attrName=None):
  global LAST
  'This will show all the help for commands ' 
  hideDisplay()
  LAST = WLS_ON.man(attrName)
  return LAST
  

def manual(attrName=None):
  global LAST
  'This will show all the help for commands ' 
  LAST = man(attrName)
  return LAST
  
def listChildTypes(parent=None):
  global LAST
  'This will show all the children types of cmo or parent' 
  hideDisplay()
  try:
      LAST = WLS_ON.listChildTypes(parent)
      return LAST
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage()) 
      
def getMBI(mbeanType=None):
  global LAST
  'This will return the model mbean info for the type specified' 
  hideDisplay()
  try:
      LAST = WLS_ON.getMBI(mbeanType)
      return LAST
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def getConfigManager():
  global LAST
  'This will return the configuration manager' 
  hideDisplay()
  try:
      LAST = WLS_ON.getConfigManager()
      return LAST
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
  

def find(name=None, type=None, searchInstancesOnly="true"):
  global LAST
  'This will show all the children types of cmo ' 
  hideDisplay()
  try:
      LAST = WLS_ON.find(name,type,searchInstancesOnly)
      return LAST
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def loadProperties(fileName):
  # verify theInterpreter is set
  if WLSTUtil.runningWLSTAsModule():
    WLS_ON.println('Cannot use loadProperties while using wlst as a module')
    return    
  global theInterpreter
  WLS_ON.loadProperties(fileName, theInterpreter)

def storeUserConfig(userConfigFile=None, userKeyFile=None, nm='false'):
  try:
    WLS_ON.storeUserConfig(userConfigFile, userKeyFile, nm)
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

from weblogic.management.scripting.utils import WLSTUtil

def state(name, type="Server"):
  global LAST
  try:
    LAST = WLS_ON.state(name,type)     
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def threadDump(writeToFile="true", fileName=None, serverName = None):
      global LAST
      result = WLS_ON.threadDump(writeToFile, fileName, serverName)
      hideDisplay()
      LAST = result
      return LAST
      
def currentTree():
    global LAST
    if domainType == tree.DEPRECATED_ADMIN_TREE:
      LAST = config
      return LAST
    elif domainType == tree.DEPRECATED_RUNTIME_TREE:
      LAST = runtime
      return LAST
    elif domainType == tree.CUSTOM_TREE:
      LAST = custom
      return LAST
    elif domainType == tree.DOMAIN_CUSTOM_TREE:
      LAST = domainCustom
      return LAST
    elif domainType == tree.JNDI_TREE:
      LAST = jndi
      return LAST
    elif domainType == tree.CONFIG_RUNTIME_TREE:
      LAST = serverConfig
      return LAST
    elif domainType == tree.RUNTIME_RUNTIME_TREE:
      LAST = serverRuntime
      return LAST
    elif domainType == tree.RUNTIME_DOMAINRUNTIME_TREE:
      LAST = domainRuntime
      return LAST
    elif domainType == tree.CONFIG_DOMAINRUNTIME_TREE:
      LAST = domainConfig
      return LAST
    elif domainType == tree.EDIT_TREE:
      LAST = edit
      return LAST
    else:
      LAST = edit
      return LAST

def addListener(mbean, attributeNames=None, logFile=None, listenerName=None):
  try:
    WLS_ON.watch(mbean, attributeNames, logFile, listenerName)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def removeListener(mbean=None, listenerName=None):
  try:
    WLS_ON.removeWatch(mbean, listenerName)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def showListeners():
  try:
    WLS_ON.showWatches()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def lookup(name, childMBeanType=None):
  global LAST
  try:
    hideDisplay()
    LAST = WLS_ON.lookup(name,childMBeanType)
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def viewMBean(mbean):
  try:
    LAST = WLS_ON.viewMBean(mbean)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      

def getPath(mbean):
  try:
    LAST = WLS_ON.getPath(mbean)
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

### Custom help commands

def addHelpCommandGroup(groupName, resourceBundleName):
  try:
    WLS_ON.addHelpCommandGroup(groupName, resourceBundleName)
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def addHelpCommand(commandName, groupName, offline='false', online='false'):
  try:
    WLS_ON.addHelpCommand(commandName, groupName, offline, online)
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

"""
End of All Information Commands
----------------------------------------------------------------------
"""

"""
Start of All Tree related commands
-----------------------------------------------------------------------
"""

def jndi(serverName=None):
  global LAST
  try:
    WLS_ON.jndi(serverName)
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def runtime():
  global LAST
  try:
    WLS_ON.cdToRuntime()
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def config():
  global LAST
  try:
    WLS_ON.cdToConfig()
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def custom(objectNamePattern=None):
  global LAST
  try:
    WLS_ON.cdToCustom(objectNamePattern)
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def domainCustom(objectNamePattern=None):
  global LAST
  try:
    WLS_ON.cdToDomainCustom(objectNamePattern)
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def adminConfig():
  global LAST
  try:
    WLS_ON.adminConfig()
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def configRuntime():
  global LAST
  try:
    WLS_ON.configRuntime()
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def serverConfig():
  global LAST
  try:
    WLS_ON.configRuntime()
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def serverRuntime():
  global LAST
  try:
    WLS_ON.runtimeRuntime()
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


def domainConfig():
  global LAST
  try:
    WLS_ON.configDomainRuntime()
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


def domainRuntime():
  global LAST
  try:
    WLS_ON.runtimeDomainRuntime()
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

# Un-documented
# @exclude
def configEdit():
  global LAST
  try:
    WLS_ON.configEdit()
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


def edit():
  configEdit()

def findService(serviceName, serviceType, location=None):
  global LAST
  try:
    LAST = WLS_ON.findService(serviceName,
                              serviceType,
                              location)
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

"""
End of All Tree related Commands
----------------------------------------------------------------------
"""

"""
Start of All LifeCycle Related Commands
------------------------------------------------------------------------
"""
def shutdown(name=None,
             entityType='Server',
             ignoreSessions='false',
             timeOut=0,
             force='false',
             block='true'):
  try:
    global cmo,domainName,domainType,showStack,version,mbs,connected,myps1
    global ncPrompt,serverName
    shutdownSuccess = WLS_ON.isShutdownSuccessful()
    if name == None:
      sdCurrentServer = WLS_ON.shutdown(serverName,
                                        entityType,
                                        ignoreSessions,
                                        timeOut,
                                        force,
                                        block)
    else:
      sdCurrentServer = WLS_ON.shutdown(name,
                                        entityType,
                                        ignoreSessions,
                                        timeOut,
                                        force,
                                        block)
    if sdCurrentServer == 1:
      connected="false"
      mbs=None
      cmo=None
      domainName=None
      domainType=None
      showStack=None
      version=None
      myps1=ncPrompt
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def startServer(adminServerName="myserver",
                domainName="mydomain",
                url=None,
                username=None,
                password=None,
                domainDir=".",
                block='true',
                timeout=120000,
                useNM='true',
                overWriteRootDir='false',
                serverLog=None,
                systemProperties=None,
                jvmArgs = None,
                spaceAsJvmArgsDelimiter = 'false'):
  global LAST
  try:
    LAST = WLS_ON.startSvr(domainName,
                           adminServerName,
                           username,
                           password,
                           url,
                           domainDir,
                           "false",
                           overWriteRootDir,
                           block,
                           timeout,
                           useNM,
                           serverLog,
                           systemProperties,
                           jvmArgs,
                           spaceAsJvmArgsDelimiter)
    return LAST
    updateGlobals()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def start(name, 
      type="Server",
      listenAddress=None,
      port=-1,
      block="true"):
  try:
    global LAST
    LAST = WLS_ON.start(name,
                 type,
                 listenAddress,
                 port,
                 block)
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def suspend(sname=None,
             ignoreSessions="false",
             timeOut=0,
             force="false",
             block = "true"):
  try:
    global LAST
    if sname == None:
      sname = serverName
    LAST = WLS_ON.suspend(sname,
                   ignoreSessions,
                   timeOut,
                   force,
                   block) 
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def resume(sname=None,
           block="true"):
  global LAST
  try:
    if sname == None:
      sname = serverName
    LAST = WLS_ON.resume(sname, block) 
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def migrate(sname,
            destinationName,
            sourceDown='true',
            destinationDown='false',
            migrationType='all'):
  try:
      if sname == None:
        sname = serverName
      WLS_ON.migrate(sname,
                 destinationName,
                 sourceDown,
                 destinationDown,
                 migrationType)
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def migrateServer(sname,
                  machineName,
                  sourceDown='false',
                  destinationDown='false'):
  try:
      WLS_ON.migrateServer(sname,
                       machineName,
                       sourceDown,
                       destinationDown)
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def migrateAll(sname,
                  destinationName,
                  sourceDown='false',
                  destinationDown='false'):
  try:
      WLS_ON.migrateAll(sname,
                       destinationName,
                       sourceDown,
                       destinationDown)
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

"""
End of All LifeCycle related Commands
----------------------------------------------------------------------
"""

"""
Start of All NodeManager Related Commands
------------------------------------------------------------------------
"""

def nmConnect(username=None,
              password=None,
              host='localhost',
              port=-1,
              domainName=None,
              domainDir=None,
              nmType='ssl',
              verbose = 'false',
              **useBootProperties):
  # connects to the node manager
  if nmService.isConnectedToNM():
    WLS_ON.println('Already connected to a Node Manager')
    return
  try:
    nmService.nmConnect(username,
                        password,
                        host,
                        port,
                        domainName,
                        domainDir,
                        nmType,
                        verbose,
                        useBootProperties)
    updateGlobals()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def nmDisconnect():
  # disconnects from Node Manager
  try:
    nmService.nmDisconnect()
    updateGlobals()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def nm():
  global LAST
  # Indicated if the user is connected to Node Manager
  try:
    hideDisplay()
    LAST = nmService.nm()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def nmEnroll(domainDir=None, nmHome = None):
  try:
    nmService.nmEnrollMachine(domainDir, nmHome)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())  

def nmKill(serverName='myserver'):
  global LAST
  try:
    hideDisplay()
    LAST = nmService.nmKill(serverName)
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def nmStart(serverName='myserver',
            domainDir = None,
            props=None,
            writer=None):
  global LAST
  try:
    hideDisplay()
    result = nmService.nmStart(serverName,
                               domainDir,
                               props,
                               writer)
    updateGlobals()
    LAST = result
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def nmServerStatus(serverName='myserver'):
  global LAST
  try:
    hideDisplay()
    LAST = nmService.nmServerStatus(serverName)
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def nmServerLog(serverName='myserver', writer=None):
  global LAST
  try:
    hideDisplay()
    LAST = nmService.nmServerLog(serverName, writer)
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
  
def nmLog(writer=None):
  global LAST
  try:
    hideDisplay()
    LAST = nmService.nmLog(writer)
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def nmVersion():
  global LAST
  try:
    hideDisplay()
    LAST = nmService.nmVersion()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def nmGenBootStartupProps(serverName=None):
  try:
    nmService.nmGenBootStartupProps(serverName)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())  

from weblogic.utils import JavaExec
from java.io import PrintStream
from java.io import File
from java.lang import System
from java.util import Properties
from weblogic.management.scripting.utils import ScriptCommands
myChild=None
def startNodeManager(verbose='true', 
                     block='false',
                     timeout=120000,
                     username=None,
                     password=None,
                     host='localhost',
                     port=-1,
                     **nmProperties):
    try:
        global myChild
        if myChild != None:
          WLS_ON.println('A Node Manager has already been started. ')
          WLS_ON.println('Cannot start another Node Manager process via WLST')
          return
        WLS_ON.setCommandType(ScriptCommands.START_NODE_MANAGER)
        nmProcess = JavaExec.createCommand("weblogic.NodeManager")
        nmProcess.addDefaultClassPath()
        if verbose=='true':
          nmProcess.addArg("-v")
        javaVendor = System.getProperty("java.vendor")
        javaDataArch = System.getProperty("sun.arch.data.model")
        doNotUseD64 = System.getProperty("do.not.use.d64")
        osName = System.getProperty("os.name")
        if doNotUseD64 is None or doNotUseD64=='false':
          if javaDataArch=='64' and (javaVendor=='Sun Microsystems Inc.' or javaVendor=='Hewlett-Packard Co.') and (osName=='Solaris' or osName=='HP-UX' or osName=='SunOS'): 
            nmProcess.addJvmArg("-d64")
        keys = nmProperties.keys()
        keys.sort()
        systemProperties = Properties()
        systemProperties.setProperty("QuitEnabled","true")
        nmDir = "."
	nmProps="{";
        for kw in keys:
          if kw == "NodeManagerHome":
            nmDir = nmProperties[kw]
          systemProperties.setProperty(kw, nmProperties[kw])
          nmProps += kw+"="+nmProperties[kw]+","
        nmProps+="}"
        nmCommand = nmProcess.getCommand()
        msg = "Launching NodeManager with: "+nmCommand+" ... "
	fmtr = WLS_ON.getWLSTMsgFormatter()
	msg = fmtr.getLaunchingNodeManager(nmProps, nmCommand)
        WLS_ON.print(msg)
        nmProcess.addSystemProps(systemProperties)
        #
        # SubProcess is exec-ed here
        #
        myChild = nmProcess.getProcess()
        #
        # startProcess is a misnomer. The process is already started.
        # It is setting up the threads to read stdout and stderr
        #
        nmLogDir = File(nmDir)
        WLSTUtil.startProcess(myChild, "NMProcess",1)
        java.lang.Thread.sleep(10000)
        WLS_ON.println('Successfully launched the Node Manager.')
        WLS_ON.println('The Node Manager process is running independent of the WLST process.')
        WLS_ON.println('Exiting WLST will not stop the Node Manager process. Please refer ')
        WLS_ON.println('to the Node Manager logs for more information.')
        WLS_ON.println('The Node Manager logs will be under '+nmLogDir.getAbsolutePath())
    except Exception, e:
        e.printStackTrace()
        WLS_ON.println('Problem starting Node Manager')
        # kill the process
        myChild.destroy()
        return
    # wait for node manager process to start if requested
    if block=='true':
        WLS_ON.println("Waiting for Node Manager to start")
        isConnected = 0
        quitTime = java.lang.System.currentTimeMillis() + float(timeout)
        finalException = None
        while not isConnected and not isTimeoutExpired(quitTime):
            try:
                nmConnect(username,password,host,port)
                isConnected = 1
            except Exception, e:
                finalException = e
                java.lang.Thread.sleep(2000)
            if not isConnected and isTimeoutExpired(quitTime):
                WLS_ON.println("Connection Failed.")
                raise finalException
    else:
        WLS_ON.println("Node Manager starting in the background")

    
# call the function here
def stopNodeManager():
    global myChild
    if myChild!=None:
        WLS_ON.setCommandType(ScriptCommands.STOP_NODE_MANAGER)
        myChild.destroy()
        myChild = None
        print 'Stopped Node Manager Process successfully'
    else:
        nmService.nmQuit()
    updateGlobals()


"""
End of All NodeManager related Commands
----------------------------------------------------------------------
"""

"""
Start of All Documented Miscellaneous commands
----------------------------------------------------------------------
"""
def configToScript(configPath=None,
                   pyPath=None,
                   overWrite="true",
                   propertiesFile=None,
                   createDeploymentScript="false",
                   convertTheseResourcesOnly="",
                   debug = "false"
                   ):
  try:
    WLS_ON.config2Py(configPath,
                     pyPath,
                     overWrite,
                     propertiesFile,
                     createDeploymentScript,
                     convertTheseResourcesOnly,
                     debug)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def saveDomain():
  try:
    java.lang.Thread.sleep(4000)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def migrateProviders(oldBeaHome=None, newBeaHome=None, verbose='false'):
  'This will migrate old security jars to new ' 
  hideDisplay()
  WLS_ON.migrateProviders(oldBeaHome, newBeaHome, verbose)

"""
End of All Documented Miscellaneous commands
----------------------------------------------------------------------
"""

"""
Start of all Un-Documented Commands
------------------------------------------------------------------------
"""
# Un-documented
# @exclude
def listCustomMBeans():
  try:
    retString = WLS_ON.listCustomMBeans()
    print ""
    updateGlobals()
    hideDisplay()
    return retString
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


# Un-documented
# @exclude
def reset():
  try:
    WLS_ON.reset()
    print ""
    updateGlobals()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


# Un-documented
# @exclude
def debug(val=None):
  try:
    WLS_ON.debug(val)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


# Un-documented
# @exclude
def getTarget(path):
  try:
    hideDisplay()
    return WLS_ON.getTarget(path)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

# Un-documented
# @exclude
def getTargetArray(type, values):
  try:
    myArr = WLS_ON.getTargetArray(type, values)
    if type.endswith('Runtime'):
      type='weblogic.management.runtime.'+type+'MBean'
    else:
      type='weblogic.management.configuration.'+type+'MBean'
    myArr1 = jarray.array(myArr,Class.forName(type))
    hideDisplay()
    return myArr1
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())




# Un-documented
# @exclude
def makePropertiesObject(value):
  try:
    hideDisplay()
    return WLS_ON.makePropertiesObject(value)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

# Un-documented
# @exclude
def makeArrayObject(value):
  try:
    hideDisplay()
    return WLS_ON.makeArrayObject(value)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

# Un-documented
# @exclude
def stopExecution(reason):
  raise "Error: "+reason



# Un-documented
# @exclude
def easeSyntax():
  WLS_ON.easeSyntax()


# Un-documented
# @exclude
class WLSTException(Exception):
  cause = None
  def __init__(self, value):
    self.value = value
  def __str__(self):
    # See CR344726.
    # return repr(self.value)
    return PyString(self.value)
  def getCause(self):
    return self.cause
  def setCause(self, acause):
    self.cause = acause
    
# Un-documented
# @exclude
def raiseWLSTException(e):
  wlste = WLSTException(e.getMessage())
  wlste.setCause(e.getCause())
  raise wlste

# Un-documented
# @exclude
def eatdisplay(dummy):
  pass

# Un-documented
# @exclude
def hideDisplay():
  sys.displayhook=eatdisplay


import weblogic.management.scripting.WLSTConstants as tree
# Un-documented
# @exclude
def evaluatePrompt():
  if WLSTUtil.runningWLSTAsModule():
    return
  global wlstPrompt,dcCalled,lastPrompt,domainName,hideProm
  global domainType,showStack,wlsversion,mbs,connected,myps1,promptt
  global ncPrompt,serverName
  if wlstPrompt=="false":
    myps1=sys.ps1
    sys.ps1=myps1
    return
  if hideProm == "false":
    if nmService.isConnectedToNM() == 1 and connected == "false":
      myps1="wls:/nm/"+str(nmService.getDomainName())+"> "
      sys.ps1 = myps1
      return
    if domainType == tree.DEPRECATED_ADMIN_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.DEPRECATED_CONFIG_PROMPT+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.DEPRECATED_RUNTIME_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.DEPRECATED_RUNTIME_PROMPT+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.CUSTOM_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.CUSTOM_PROMPT+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.DOMAIN_CUSTOM_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.DOMAIN_CUSTOM_PROMPT+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.JNDI_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.JNDI_PROMPT+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.CONFIG_RUNTIME_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.CONFIG_RUNTIME_PROMPT+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.RUNTIME_RUNTIME_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.RUNTIME_RUNTIME_PROMPT+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.RUNTIME_DOMAINRUNTIME_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.RUNTIME_DOMAINRUNTIME_PROMPT+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.CONFIG_DOMAINRUNTIME_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.CONFIG_DOMAINRUNTIME_PROMPT+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.EDIT_TREE:
      if WLS_ON.isEditSessionInProgress() == 1:
        myps1="wls:/"+str(domainName)+"/"+tree.EDIT_PROMPT+promptt+" !> "
      else:
        myps1="wls:/"+str(domainName)+"/"+tree.EDIT_PROMPT+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.DEPRECATED_CONFIG_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.DEPRECATED_CONFIG_PROMPT+promptt+"> "
      sys.ps1=myps1
  elif hideProm == "true":
    myps1="wls:/> "
    sys.ps1=myps1
  if connected=="false":
    # see if readTemplate or anything is intact
    offlinePrompt = java.lang.String(WLS.getAbsPwd())
    if offlinePrompt != None and offlinePrompt.length()!= 0:
      myps1 = "wls:/offline"+str(offlinePrompt)+"> "
    else:
      myps1=ncPrompt
    sys.ps1=myps1

# Un-documented
# @exclude
def updateGlobals():
  global isAdminServer,version,username,cmo,home,adminHome,wlstPrompt,dcCalled
  global lastPrompt,domainName,hideProm,domainType,showStack,wlsversion
  global mbs,connected,myps1,promptt,ncPrompt,serverName,cmgr,domainRuntimeService
  global hideProm,editService,runtimeService,typeService, scriptMode
  cmo = WLS_ON.getCmo()
  promptt = WLS_ON.getPrompt()
  domainName = WLS_ON.getDomainName()
  connected = WLS_ON.getConnected()
  domainType = WLS_ON.getDomainType()
  mbs = WLS_ON.getMBeanServer()
  home = WLS_ON.getHome()
  adminHome = WLS_ON.getAdminHome()
  serverName = WLS_ON.getServerName()
  wlsversion = WLS_ON.getVersion()
  username = java.lang.String(WLS_ON.getUsername_bytes())
  cmgr = WLS_ON.getConfigManager()
  version = wlsversion
  isAdminServer = WLS_ON.isAdminServer()
  domainRuntimeService = WLS_ON.getDomainRuntimeServiceMBean()
  runtimeService = WLS_ON.getRuntimeServiceMBean()
  editService = WLS_ON.getEditServiceMBean()
  typeService = WLS_ON.getMBeanTypeService()
  scriptMode = WLS_ON.getScriptMode()
  evaluatePrompt()

# Un-documented
# @exclude
def restoreDisplay():
  'This will restore the Display to Default' 
  global oldhook,myps1
  sys.displayhook=oldhook
  myps1=sys.ps1
  
# Un-documented
# @exclude
def viewDebugScope():
  'This will bring the debug scope in a swing format' 
  weblogic.diagnostics.debug.DebugScopeViewer.main(None)
  

# Un-documented
# @exclude
def startSvr(domainName='mydomain', serverName='myserver', usrName='weblogic', passwrd='weblogic', url='t3://localhost:7001', generateDefaultConfig='true', rootDir='.'):
  'This will start a new server' 
  WLS_ON.startSvr(domainName, serverName, usrName, passwrd, url, generateDefaultConfig, rootDir, 'false',0)
  

# Un-documented
# @exclude
def jsr77():
  try:
    WLS_ON.jsr77()
    print ""
    updateGlobals()
    hideDisplay()
    return cmo
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      

# Determine if the connect retry count has reached the limit.
# Un-documented
# @exclude
def isTimeoutExpired(quitTime):
    return java.lang.System.currentTimeMillis() > quitTime
        
        
# Un-documented
# @exclude
def showExcluded(value="true"):
    WLS_ON.setShowExcluded(value)
    
# Un-documented
# @exclude
def dumpMBeans(value=None):
    WLS_ON.dumpMBeans(value)
    
# Un-documented
# @exclude
def skipSingletonCd(value="false"):
    WLS_ON.skipSingletonCd(value)

# Un-documented
# @exclude
def hideDumpStack(value="true"):
    WLS_ON.setHideDumpStack(value)
    
# Un-documented
# @exclude
def setDumpStackThrowable(value=None):
    WLS_ON.setDumpStackThrowable(value)
    

# call the function here
# Un-documented
# @exclude
def wlstExplorer(verbose="false"):
  #WLS_ON.wlstTree(verbose)
  print "Not supported in the GA version. Please download the jar from dev2dev"
  

def exportDiagnosticDataFromServer(**dict) :
    
    dict.setdefault('logicalName', 'ServerLog')
    dict.setdefault('query', '')
    dict.setdefault('exportFileName', 'export.xml')
    dict.setdefault('beginTimestamp', 0L)
    dict.setdefault('endTimestamp', Long.MAX_VALUE)

    logicalName = dict.get('logicalName')
    query = dict.get('query')
    exportFileName = dict.get('exportFileName')
    beginTimestamp = dict.get('beginTimestamp')
    endTimestamp = dict.get('endTimestamp')    

    WLS_ON.exportDiagnosticDataFromServer(logicalName, query, beginTimestamp, endTimestamp, exportFileName)

    WLS_ON.println("Exported diagnostic data to " + exportFileName)
  
def getAvailableCapturedImages() :
  result = WLS_ON.getAvailableCapturedImages()
  return result

def saveDiagnosticImageCaptureFile(imageName, outputFile=None) :
  destFile=outputFile
  if destFile == None:
    destFile=imageName
  WLS_ON.println("Retrieving " + imageName + " to local path " + destFile);
  WLS_ON.saveDiagnosticImageCaptureFile(imageName, destFile)

def saveDiagnosticImageCaptureEntryFile(imageName, imageEntryName, outputFile=None) :
  destFile=outputFile
  if destFile == None:
    destFile=imageEntryName
  WLS_ON.println("Retrieving entry " + imageEntryName + " from " + imageName + " to local path " + destFile);
  WLS_ON.saveDiagnosticImageCaptureEntryFile(imageName, imageEntryName, destFile)

"""
End of all Un-Documented Commands
"""
