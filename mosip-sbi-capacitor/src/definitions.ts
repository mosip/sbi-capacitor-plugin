export interface CapacitorIntentOptions {
  url?: string;
  methodType?: string;
  action?: string;
  extraKey?: string;
  extraValue?: string;    
}
export interface MosipSbiCapacitorPlugin {
  pluginName: string;
  startActivity(options: CapacitorIntentOptions): Promise<void>;
}
