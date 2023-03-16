export interface CapacitorIntentOptions {
  url?: string;
  methodType?: string;
  action?: string;
  requestKey?: string;
  requestValue?: string;    
}
export interface MosipSbiCapacitor {
  pluginName: string;
  startActivity(options: CapacitorIntentOptions): Promise<void>;
}
