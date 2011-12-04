package bot;

import java.io.*;
import java.net.*;
import java.util.*;

public class CrawlMaster{
public CrawlMaster(String startingAddress){
try{start=new URL(startingAddress);}catch(Exception h){h.printStackTrace();}
urlList=new ArrayList();
}

private URL start;
private List urlList;
private static final String HREF="href=\"";

public void startTrace(){
//connect(), go into loop and put url into urlList, search the page for more url and add them into list

}

public void test() throws IOException{
BufferedReader in=new BufferedReader(new InputStreamReader(start.openStream()));
String s=""; System.out.println("<param>");
String pz=new BufferedReader((new InputStreamReader(System.in))).readLine();
while((s=in.readLine())!=null){pullURL(s,pz);}
in.close();
printList(urlList);
System.out.println("Filter using?<key1>;<key2>;...");
String filList=new BufferedReader((new InputStreamReader(System.in))).readLine();//
String keyList[]=filList.split(";");						 //
List fg=filter(keyList,urlList);						//
//List fg=filter(new BufferedReader((new InputStreamReader(System.in))).readLine(),urlList);
printList(fg);
System.out.println("Download? [y]");
pz=new BufferedReader((new InputStreamReader(System.in))).readLine();
if(pz.equals("y")){
System.out.println("<path>?");
pz=new BufferedReader((new InputStreamReader(System.in))).readLine();
long mdl=download(fg,pz);
System.out.println("Done. Downloaded "+mdl+" B");
}
//}
}

//public List getList(){return urlList;}

private List filter(String s, List x){
List li=new ArrayList();
for(int i=0;i<x.size();i++){
String d=(String)x.get(i);
if(d.indexOf(s)!=-1){
li.add(d);}
}
return li;
}

private List filter(String s[], List x){
List p=x,q=null;
for(int i=0;i<s.length;i++){
q=filter(s[i],p);
p=q;
}
return q;
}

public void printList(List u){
System.out.println("------------");
for(int i=0;i<u.size();i++){
System.out.println((String)u.get(i));
}
}

private long getLength(URL x) throws IOException{
return x.openConnection().getContentLength();
}

private boolean canDot(long downed, long tot){
int r=(int)(downed*100/tot);
if(r==20||r==40||r==60||r==80||r==100)
	return true;
return false;
}

private void pullURL(String s, String param){
//go into an indefinite loop
String sub;
while(true){
int k=s.indexOf(param);
if(k==-1) break;
///////////////////////////
sub=s.substring(k+param.length()); //start point of url
int l=sub.indexOf("\"");
urlList.add(sub.substring(0,l));
s=new String(sub);
}
}

private String getExtension(String s){
int f=s.lastIndexOf("/");
String k=s.substring(f+1);
return k;
}

private long download(List s, String path) throws IOException{
long master=0;
for(int i=0;i<s.size();i++){
URL dlFile=new URL((String)s.get(i));
//BufferedReader fileIn=new BufferedReader(new InputStreamReader(dlFile.openStream()));
//PrintWriter outFile=new PrintWriter(new BufferedWriter(new FileWriter());
DataInputStream fileIn=new DataInputStream(new BufferedInputStream(dlFile.openStream()));
DataOutputStream outFile=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path+getExtension((String)s.get(i)))));
long tot=getLength(new URL((String)s.get(i)));
long downed=0;
System.out.print("..Downloading "+(i+1)+" of "+s.size()+" files; ");
System.out.print("size "+tot+" B");
System.out.flush();
while(true){
try{
outFile.writeByte(fileIn.readByte());
downed++;
//if(canDot(downed,tot)){System.out.print(".");}
}catch(EOFException eof){
fileIn.close();
outFile.close();
System.out.println("#");
master+=tot;
break;}
}

}
return master;
}


public static void main(String[] rags) throws Exception{
System.out.print("Address?");
CrawlMaster cl=new CrawlMaster(new BufferedReader((new InputStreamReader(System.in))).readLine());
cl.test(); //successful
}
}

