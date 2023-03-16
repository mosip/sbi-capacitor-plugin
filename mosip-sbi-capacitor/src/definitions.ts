export interface CapacitorIntentOptions {
  url?: string;
  methodType?: string;
  action?: string;
  requestKey?: string;
  requestValue?: string;    
}
export interface MosipSbiCapacitorPlugin {
  pluginName: string;
  startActivity(options: CapacitorIntentOptions): Promise<void>;
}
