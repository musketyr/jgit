import org.eclipse.jgit.diff.RawTextIgnoreAllWhitespace;
import org.eclipse.jgit.diff.RawTextIgnoreLeadingWhitespace;
import org.eclipse.jgit.diff.RawTextIgnoreTrailingWhitespace;
import org.eclipse.jgit.diff.RawTextIgnoreWhitespaceChange;
	@Option(name = "--ignore-space-at-eol")
	private boolean ignoreWsTrailing;

	@Option(name = "--ignore-leading-space")
	private boolean ignoreWsLeading;

	@Option(name = "-b", aliases = { "--ignore-space-change" })
	private boolean ignoreWsChange;

	@Option(name = "-w", aliases = { "--ignore-all-space" })
	private boolean ignoreWsAll;


		byte[] aRaw = getRawBytes(id1);
		byte[] bRaw = getRawBytes(id2);

		if (RawText.isBinary(aRaw) || RawText.isBinary(bRaw)) {
			out.println("Binary files differ");
			return;
		}

		RawText a = getRawText(aRaw);
		RawText b = getRawText(bRaw);
	private byte[] getRawBytes(ObjectId id) throws IOException {
			return new byte[] {};
		return db.openBlob(id).getCachedBytes();
	private RawText getRawText(byte[] raw) {
		if (ignoreWsAll)
			return new RawTextIgnoreAllWhitespace(raw);
		else if (ignoreWsTrailing)
			return new RawTextIgnoreTrailingWhitespace(raw);
		else if (ignoreWsChange)
			return new RawTextIgnoreWhitespaceChange(raw);
		else if (ignoreWsLeading)
			return new RawTextIgnoreLeadingWhitespace(raw);
		else
			return new RawText(raw);
	}
}