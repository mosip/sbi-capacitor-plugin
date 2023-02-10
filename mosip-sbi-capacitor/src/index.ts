import { registerPlugin } from '@capacitor/core';

import type { MosipSbiCapacitorPlugin } from './definitions';

const MosipSbiCapacitor = registerPlugin<MosipSbiCapacitorPlugin>('MosipSbiCapacitor', {
});

export * from './definitions';
export { MosipSbiCapacitor };
