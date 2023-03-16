import { registerPlugin } from '@capacitor/core';

import type { MosipSbiCapacitor } from './definitions';

const MosipSbiCapacitorPlugin = registerPlugin<MosipSbiCapacitor>('MosipSbiCapacitorPlugin', {
});

export * from './definitions';
export { MosipSbiCapacitorPlugin };
