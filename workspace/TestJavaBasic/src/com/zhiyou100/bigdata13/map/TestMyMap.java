package com.zhiyou100.bigdata13.map;

public class TestMyMap<K,V> {
	Entry<K,V>[] entrys;
	int size = 10;
	int realLength = 0;
	public TestMyMap(){
		entrys = new Entry[size];
	}
	@Override
	public String toString() {
		String s = "[";
		for(int i = 0;i<realLength;i++){
			s+=(entrys[i].toString()+",");
		}
		/*for(Entry en : entrys){
			s+=(en.toString()+",");
		}*/
		s = s.substring(0, s.length()-1);
		s+="]";
		return s;
	}
	
	public Object put(K k ,V v){
		//如果k和v为null，则不能放入
		if( k == null || v == null) return null;
		if(realLength == 0){
			Entry<K, V> e = new Entry<K, V>(v, k);
			entrys[0] = e;
			realLength++;
		}
		for(int i = 0;i<realLength;i++){
			Entry<K,V> e = entrys[i];
			//判断key是否已经存在，存在就返回老值，放入新值，不存在就直接插入
			K key = e.getKey();
			if(key.hashCode() == k.hashCode()&& key.equals(k)){
				V oldValue = e.value;
				e.value = v;
				return oldValue;
			}else{
				Entry<K, V> en = new Entry<K, V>(v, k);
				if(realLength<entrys.length){
					entrys[realLength] = en;
					realLength++;
				}else{
					Entry<K,V>[] oldEntrys = entrys;
					entrys = new Entry[realLength+10];
					for(int j = 0;j<realLength-1;j++){
						entrys[j] = oldEntrys[j];
					}
					entrys[realLength] = en;
					realLength++;
				}
			}
		}
		return null;
	}
	public static void main(String[] args) {
		TestMyMap<String, String> m = new TestMyMap<String, String>();
		m.put("zhangsan1", "1");
		m.put("zhangsan2", "2");
		m.put("zhangsan1", "3");
		System.out.println(m);
	}
	
	
	
	
	
	static class Entry<K,V>{
		V value;
		K key;
		public V getValue() {
			return value;
		}
		public void setValue(V value) {
			this.value = value;
		}
		public K getKey() {
			return key;
		}
		public void setKey(K key) {
			this.key = key;
		}
		public Entry(V value, K key) {
			super();
			this.value = value;
			this.key = key;
		}
		public Entry() {
			super();
		}
		@Override
		public String toString() {
			return key + "=" + value;
		}
		
	}

}
