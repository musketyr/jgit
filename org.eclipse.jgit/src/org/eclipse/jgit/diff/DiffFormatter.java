import java.io.InputStream;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.lib.CoreConfig;
import org.eclipse.jgit.util.IO;
	private long bigFileThreshold = 50 * 1024 * 1024;


		CoreConfig cfg = db.getConfig().get(CoreConfig.KEY);
		bigFileThreshold = cfg.getStreamFileThreshold();
	/**
	 * Set the maximum file size that should be considered for diff output.
	 * <p>
	 * Text files that are larger than this size will not have a difference
	 * generated during output.
	 *
	 * @param bigFileThreshold
	 *            the limit, in bytes.
	 */
	public void setBigFileThreshold(long bigFileThreshold) {
		this.bigFileThreshold = bigFileThreshold;
	}

		String oldName = quotePath("a/" + ent.getOldPath());
		String newName = quotePath("b/" + ent.getNewPath());
			o.write(encode("rename from " + quotePath(ent.getOldPath())));
			o.write(encode("rename to " + quotePath(ent.getNewPath())));
			o.write(encode("copy from " + quotePath(ent.getOldPath())));
			o.write(encode("copy to " + quotePath(ent.getNewPath())));
		case MODIFY:
			int score = ent.getScore();
			if (0 < score && score <= 100) {
				o.write(encodeASCII("dissimilarity index " + (100 - score)
						+ "%"));
				o.write('\n');
			}
			break;

			ObjectLoader ldr = db.open(id.toObjectId());
			if (!ldr.isLarge())
				return ldr.getCachedBytes();

			long sz = ldr.getSize();
			if (sz < bigFileThreshold && sz < Integer.MAX_VALUE) {
				byte[] buf;
				try {
					buf = new byte[(int) sz];
				} catch (OutOfMemoryError noMemory) {
					LargeObjectException e;

					e = new LargeObjectException(id.toObjectId());
					e.initCause(noMemory);
					throw e;
				}
				InputStream in = ldr.openStream();
				try {
					IO.readFully(in, buf, 0, buf.length);
				} finally {
					in.close();
				}
				return buf;
			}