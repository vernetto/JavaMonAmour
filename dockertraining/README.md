
Difference between VMs and Containers, between Hypervisors and Container Managers




External links

https://en.wikipedia.org/wiki/Docker_(software) wikipedia
https://www.docker.com/  docker main site
https://hub.docker.com/ Docker Hub

https://hub.docker.com/search/?isAutomated=0&isOfficial=0&page=1&pullCount=1&q=weblogic&starCount=0  weblogic on docker hub
take this https://hub.docker.com/r/ismaleiva90/weblogic12/


•	Docker provides the basic container management API and the container image file format.
•	Kubernetes manages a cluster of hosts (physical or virtual) that run containers. It works with resources that describe multi-container applications composed of multiple resources, and how they interconnect.
•	Etcd is a distributed key-value store, used by Kubernetes to store configuration and state information about the containers and other resources inside the OpenShift cluster.



An OpenShift cluster is a set of node servers that run containers and are centrally managed by a set of master servers. A server can act as both a master and a node, but those roles are usually segregated for increased stability.

![Openshift architecture](OCPArchitectureCapture.JPG)

![Openshift master and nodes](OCPMasterNodesCapture.JPG)


Kubernetes nodes= minions

OpenShift masters run the Kubernetes master services and Etcd daemons, while the nodes run the Kubernetes kubelet and kube-proxy daemons. While not shown in the figure, the masters are also nodes themselves. Scheduler and Management/Replication in the figure are Kubernetes master services, while Data Store is the Etcd daemon.

The kubelet is the primary “node agent” that runs on each node. (https://kubernetes.io/docs/reference/generated/kubelet/)

The Kubernetes scheduling unit is the pod, which is a grouping of containers sharing a virtual network device, internal IP address, TCP/UDP ports, and persistent storage. A pod can be anything from a complete enterprise application, including each of its layers as a distinct container, to a single microservice inside a single container. For example, a pod with one container running PHP under Apache and another container running MySQL.

Kubernetes manages replicas to scale pods. A replica is a set of pods sharing the same definition. For example, a replica consisting of many Apache and PHP pods running the same container image could be used for horizontally scaling a web application.


oc new-app bla: there is no "application" object in OS, it's only related resources, sharing the same label app=bla
oc new-app --docker-image=registry.com:5000/openshift/hello-openshift --insecure-registry --name=hello

to display registry, console and router pods:
**oc project default**
**oc get pods**


SDN software defined network



Storage: glouster

PersistentVolume
PersistentVolumeClaim

https://docs.openshift.org/latest/dev_guide/persistent_volumes.html
PersistentVolume objects from sources such as GCE Persistent Disk, AWS Elastic Block Store (EBS), and NFS mounts.

oc get pvc
oc get pv

OCP Volume Providers

dig
systemctl status firewalld
systemctl status NetworkManager
iptables -F
ssh-keygen -f /root/.ssh/id_rsa -N ''
ssh-copy-id root@master

https://www.howtogeek.com/177621/the-beginners-guide-to-iptables-the-linux-firewall/


change registry
oc set image --source.docker dc/registry-console \ registry-console=workstation.lab.example.com:5000\ /openshift3/registry-console:3.5


oc get pods
NAME                            READY     STATUS      RESTARTS   AGE
docker-registry-1-qf0tz         1/1       Running     0          1m
persistent-volume-setup-p5dng   0/1       Completed   0          1m
router-1-fbwzn                  1/1       Running     0          1m


oc new-project mlbparks
oc create -f https://raw.githubusercontent.com/gshipley/openshift3mlbparks/master/mlbparks-template-wildfly.json
oc new-app mlbparks-wildfly

oc get -o wide pods --all-namespaces

#delete all internal images
oc delete is -n openshift --all

oc delete project bla

1 pod = 1 unique IP (can change with time!)
service = LB for 1 or more pods. Has also unique STABLE IP

**oc scale --replicas=2 dc mlbparks**

**oc get pods -o wide**

```
NAME                       READY     STATUS      RESTARTS   AGE       IP           NODE
mlbparks-1-bb29n           1/1       Running     0          4m        172.17.0.2   localhost
mlbparks-1-build           0/1       Completed   0          18h       172.17.0.2   localhost
mlbparks-1-q94r3           1/1       Running     0          18h       172.17.0.6   localhost
mlbparks-mongodb-1-vdxrs   1/1       Running     0          18h       172.17.0.5   localhost
```


**oc get svc mlbparks**

```
NAME       CLUSTER-IP     EXTERNAL-IP   PORT(S)    AGE
mlbparks   172.30.90.69   <none>        8080/TCP   19h
```


**oc new-project cakephp**
**oc new-app openshift/templates/cakephp.json -p SOURCE_REPOSITORY_URL=https://github.com/vernetto/cakephp-ex.git**


**oc new-project myruby**
**oc new-app centos/ruby-22-centos7~https://github.com/openshift/ruby-hello-world.git**
**oc expose service ruby-hello-world**


**oc describe svc ruby-hello-world**

```Name:			ruby-hello-world
Namespace:		myruby
Labels:			app=ruby-hello-world
Selector:		app=ruby-hello-world,deploymentconfig=ruby-hello-world
Type:			ClusterIP
IP:			172.30.223.99
Port:			8080-tcp	8080/TCP
Endpoints:		172.17.0.8:8080
Session Affinity:	None
No events.
```

**oc rsh ruby-hello-world-1-w1wt6**


Routers:
Edge Termination, Pass-Through Termination, Re-encryption termination


to create an edge-terminated route:

**openssl genrsa -out example.key 2048**
**openssl req -new -key example.key -out example.csr -subj "/C=US/ST=CA/L=Los Angeles/0=Example/OU=IT/CN=test.example.com"**
**openssl x509 -req -days 366 -in example.csr -signkey example.key -out example.crt**
**oc create route edge --service=test  --hostname=test.example.com --key=example.key --cert=example.crt**

**oc edit route ruby-hello-world** 

**oc whoami**

to delete an application (not a project)
**delete all -l app=hello-world**

**oc get all**


[centos@localhost ~]$ **oc project default**

[centos@localhost ~]$ **oc get all**

```
NAME                 REVISION   DESIRED   CURRENT   TRIGGERED BY
dc/docker-registry   1          1         1         config
dc/router            1          1         1         config

NAME                   DESIRED   CURRENT   READY     AGE
rc/docker-registry-1   1         1         1         23h
rc/router-1            1         1         1         23h

NAME                  CLUSTER-IP      EXTERNAL-IP   PORT(S)                   AGE
svc/docker-registry   172.30.1.1      <none>        5000/TCP                  23h
svc/kubernetes        172.30.0.1      <none>        443/TCP,53/UDP,53/TCP     23h
svc/router            172.30.54.232   <none>        80/TCP,443/TCP,1936/TCP   23h

NAME                               READY     STATUS      RESTARTS   AGE
po/docker-registry-1-qf0tz         1/1       Running     0          23h
po/persistent-volume-setup-p5dng   0/1       Completed   0          23h
po/router-1-fbwzn                  1/1       Running     0          23h
```

oc export dc ruby-hello-world > rubydc.yaml 
oc create -f rubydc.yaml


oc exec ruby-hello-world-1-w1wt6 "hostname"


/var/lib/docker/devicemapper/mnt/dbc5e6adb33a64cb2f1aa68c3fac5fb9ba0eb6225486b054189dfa787f572ec4/rootfs/run/secrets/kubernetes.io

#list all templates, imagestreams
oc new-app -L (or --list)

#help on new-app
oc new-app -h


**oc exec ruby-hello-world-1-w1wt6 cat /etc/resolv.conf**
nameserver 172.30.0.1
search myruby.svc.cluster.local svc.cluster.local cluster.local homenet.telecomitalia.it
options ndots:5


**oc exec ruby-hello-world-1-w1wt6 -it bash**


**sosreport** 
**oc adm diagnostics** 

oc rsync /home/user/source devpod1234:/src -c <container>

oc port-forward <pod> local_port:remote_port



**systemctl status docker.service**

```
● docker.service - Docker Application Container Engine
   Loaded: loaded (/usr/lib/systemd/system/docker.service; enabled; vendor preset: disabled)
   Active: active (running) since Thu 2017-12-28 21:15:51 CET; 4 days ago
     Docs: https://docs.docker.com
 Main PID: 1252 (dockerd)
   Memory: 172.7M
   CGroup: /system.slice/docker.service
           ├─ 1252 /usr/bin/dockerd
           ├─ 1360 docker-containerd --config /var/run/docker/containerd/containerd.toml
           ├─ 8507 docker-containerd-shim --namespace moby --workdir /var/lib/docker/containerd/daemon/io.containerd.runtime.v1.linux/moby/5c8523b85cc7a386ceb940030039d4747b201ceffc452bc6635313074334946d --ad...
           ├─ 8615 docker-containerd-shim --namespace moby --workdir /var/lib/docker/containerd/daemon/io.containerd.runtime.v1.linux/moby/170abde04e5e700df1a3473bfc0d8f70856e378617d69b8bbf14d85dd60d1f0f --ad...
           ├─19178 docker-containerd-shim --namespace moby --workdir /var/lib/docker/containerd/daemon/io.containerd.runtime.v1.linux/moby/0f4bacf9957622ef841853da31e4a7afb995ebf3edad9e448f61b5203f8e9dd1 --ad...
           ├─19292 docker-containerd-shim --namespace moby --workdir /var/lib/docker/containerd/daemon/io.containerd.runtime.v1.linux/moby/8ba7b7bdcd11dd3d25d1eda835805982d6fd68c7450e2b55cb5fd4413a110e7a --ad...
           ├─23009 docker-containerd-shim --namespace moby --workdir /var/lib/docker/containerd/daemon/io.containerd.runtime.v1.linux/moby/d1b790792263bc2e2ca3efdd7b559b95b8680f360e813dec374d54c25835df56 --ad...
           ├─24199 docker-containerd-shim --namespace moby --workdir /var/lib/docker/containerd/daemon/io.containerd.runtime.v1.linux/moby/25fa62f2808d7b6fd5865ef9d121694d67a6ffb3c0d8b2c18c6127e72548f7fa --ad...
           ├─24464 docker-containerd-shim --namespace moby --workdir /var/lib/docker/containerd/daemon/io.containerd.runtime.v1.linux/moby/16885fcc7d541d46e4d7cecc3c173852821db72ba2afc86c763ad9d5cb47d2b3 --ad...
           ├─24727 docker-containerd-shim --namespace moby --workdir /var/lib/docker/containerd/daemon/io.containerd.runtime.v1.linux/moby/458fddd2bde8de573ae3257c0fa1cd17eafa099c2b8f805276b9fbfa711cc819 --ad...
           ├─24856 docker-containerd-shim --namespace moby --workdir /var/lib/docker/containerd/daemon/io.containerd.runtime.v1.linux/moby/24b8bf1d12ae822938773ef4cfbe79f59bd72d9d665369e89b616ed360a80ec9 --ad...
           ├─28303 docker-containerd-shim --namespace moby --workdir /var/lib/docker/containerd/daemon/io.containerd.runtime.v1.linux/moby/29ebd088ca518fee0fa3e1851735bba25344f0ae6084c63d0b4cd5fa1d1e07b3 --ad...
           ├─28414 docker-containerd-shim --namespace moby --workdir /var/lib/docker/containerd/daemon/io.containerd.runtime.v1.linux/moby/64c08e030e3c798ebd64be734b33cf4362698fc731caf50674428d4c268c65a9 --ad...
           ├─29846 docker-containerd-shim --namespace moby --workdir /var/lib/docker/containerd/daemon/io.containerd.runtime.v1.linux/moby/a1f27d368e0271e689258989137831ee0d61c6560895d0f12db2f14691420dad --ad...
           └─29974 docker-containerd-shim --namespace moby --workdir /var/lib/docker/containerd/daemon/io.containerd.runtime.v1.linux/moby/397022d1071568db1ab22b06f2e1f3256f5847e0771d5eeaaecf368be4b12d7d --ad...

```

oc adm policy remove-cluster-role-from-group self-provisioner system:authenticated system:authenticated:oauth

oc adm policy add-cluster-role-to-group self-provisioner system:authenticated system:authenticated:oauth


**oc get scc**

```
NAME               PRIV      CAPS      SELINUX     RUNASUSER          FSGROUP     SUPGROUP    PRIORITY   READONLYROOTFS   VOLUMES
anyuid             false     []        MustRunAs   RunAsAny           RunAsAny    RunAsAny    10         false            [configMap downwardAPI emptyDir persistentVolumeClaim projected secret]
hostaccess         false     []        MustRunAs   MustRunAsRange     MustRunAs   RunAsAny    <none>     false            [configMap downwardAPI emptyDir hostPath persistentVolumeClaim projected secret]
hostmount-anyuid   false     []        MustRunAs   RunAsAny           RunAsAny    RunAsAny    <none>     false            [configMap downwardAPI emptyDir hostPath nfs persistentVolumeClaim projected secret]
hostnetwork        false     []        MustRunAs   MustRunAsRange     MustRunAs   MustRunAs   <none>     false            [configMap downwardAPI emptyDir persistentVolumeClaim projected secret]
nonroot            false     []        MustRunAs   MustRunAsNonRoot   RunAsAny    RunAsAny    <none>     false            [configMap downwardAPI emptyDir persistentVolumeClaim projected secret]
privileged         true      [*]       RunAsAny    RunAsAny           RunAsAny    RunAsAny    <none>     false            [*]
restricted         false     []        MustRunAs   MustRunAsRange     MustRunAs   RunAsAny    <none>     false            [configMap downwardAPI emptyDir persistentVolumeClaim projected secret]
```

**oc describe scc anyuid**

``` 
Name:						anyuid
Priority:					10
Access:						
  Users:					<none>
  Groups:					system:cluster-admins
Settings:					
  Allow Privileged:				false
  Default Add Capabilities:			<none>
  Required Drop Capabilities:			MKNOD,SYS_CHROOT
  Allowed Capabilities:				<none>
  Allowed Volume Types:				configMap,downwardAPI,emptyDir,persistentVolumeClaim,projected,secret
  Allow Host Network:				false
  Allow Host Ports:				false
  Allow Host PID:				false
  Allow Host IPC:				false
  Read Only Root Filesystem:			false
  Run As User Strategy: RunAsAny		
    UID:					<none>
    UID Range Min:				<none>
    UID Range Max:				<none>
  SELinux Context Strategy: MustRunAs		
    User:					<none>
    Role:					<none>
    Type:					<none>
    Level:					<none>
  FSGroup Strategy: RunAsAny			
    Ranges:					<none>
  Supplemental Groups Strategy: RunAsAny	
    Ranges:					<none>

```


oc adm policy add-scc-to-user scc_name user_name
oc adm policy add-scc-to-group scc_name group_name

oc create user demo_user

oc policy add-role-to-user edit demo_user


