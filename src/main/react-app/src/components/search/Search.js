import {Button, FormControl, InputGroup} from 'react-bootstrap'
import {BsCalendar, BsImage, BsSearch} from 'react-icons/bs'
import './search.scss'
import {createRef, forwardRef, useEffect, useState} from 'react'
import DatePicker from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.min.css'
import {AiOutlineCloseCircle} from 'react-icons/all'
import SearchFilter from './SearchFilter'
import {useLazyFindAuthorQuery, useLazyFindHashtagQuery, useLazyFindImageQuery} from '../../redux/api/baseApi'
import {useHistory} from 'react-router-dom'

const Search = ({setSearchImageResults, setSearchFired, clearSearch, setClearSearch}) => {
  // Fetch results
  const searchInput = createRef()
  const [findImages, foundImages] = useLazyFindImageQuery()
  useEffect(() => setSearchImageResults(foundImages), [setSearchImageResults, foundImages])

  // Search for specific image size
  const [currentImageSize, setCurrentImageSize] = useState(1)
  const imageSize = [{size: 1, value: 'NORMAL'}, {size: 1.3, value: 'BIG'}, {size: 0.7, value: 'SMALL'}]

  const changeImageSize = () => {
    let currentImage = imageSize.find(({size}) => size === currentImageSize) || {size: 1}
    let currentSize = currentImage.size

    // Bugfix: Implementation that use array index as currentImageSize worked unexpectedly in some situations
    let nextSize = (s) => {
      switch (s) {
        case 1:
          return 1.3
        case 1.3:
          return 0.7
        default:
          return 1
      }
    }
    setCurrentImageSize(nextSize(currentSize))
  }

  // Search for specific date
  const [searchDate, setSearchDate] = useState([undefined, undefined])
  const [startDate, endDate] = searchDate
  const SearchElement = forwardRef(({value, onClick}, ref) => (
    <InputGroup.Text className="search-action" ref={ref}>
      <BsCalendar onClick={onClick}/>
      {value && <span className="search-date-value">{value}</span>}
      {value && <Button className="search-date-clear" onClick={() => setSearchDate([undefined, undefined])}
                        variant="link"><AiOutlineCloseCircle className="mb-1"/></Button>}
    </InputGroup.Text>
  ))

  // Search for author or hashtag
  const [authors, setAuthors] = useState([])
  const [foundAuthors, setFoundAuthors] = useState({isSuccess: false})
  const [findAuthor, findAuthorResult] = useLazyFindAuthorQuery()
  useEffect(() => setFoundAuthors(findAuthorResult), [findAuthorResult])

  const [hashtags, setHashtags] = useState([])
  const [foundHashtags, setFoundHashtags] = useState({isSuccess: false})
  const [findHashtag, findHashtagResult] = useLazyFindHashtagQuery()
  useEffect(() => setFoundHashtags(findHashtagResult), [findHashtagResult])

  const findAuthorOrHashtag = (e) => {
    let query = e.target.value

    if (query.length < 3) {
      return
    }

    if (query.startsWith('@')) {
      findAuthor(query.substring(1))
    } else if (query.startsWith('#')) {
      findHashtag(query.substring(1))
    }
  }

  const clearSearchField = () => {
    setFoundAuthors({isSuccess: false})
    setFoundHashtags({isSuccess: false})
    searchInput.current.value = ''
  }

  // Perform search
  const history = useHistory()
  const search = () => {
    let args = {}

    if (imageSize.find(({size, value}) => (size === currentImageSize && value !== 'NORMAL'))) {
      args.size = imageSize.find(({size}) => size === currentImageSize).value
    }
    args.authors = authors.map(({id}) => id).join(',')
    args.hashtags = hashtags.map(({id}) => id).join(',')
    // Using searchDate variable because there is a Date object stored
    if (startDate) {
      args.dateFrom = searchDate[0].toISOString()
    }
    if (endDate) {
      args.dateTo = searchDate[1].toISOString()
    }

    findImages(args)
    setSearchFired(true)
    history.push('/')
  }

  // Clear search component
  useEffect(() => {
    if (!clearSearch) {
      return;
    }

    if (searchInput.current) {
      searchInput.current.value = ''
    }
    setAuthors([])
    setHashtags([])
    setSearchDate([undefined, undefined])
    setCurrentImageSize(1)
    setClearSearch(false)
  }, [clearSearch, setClearSearch, searchInput])

  return (
    <div className="mx-3 my-2 flex-grow-1 search position-relative">
      <InputGroup className="search-input">
        <FormControl ref={searchInput} placeholder="Type @ to search authors or # to search hashtags"
                     onChange={findAuthorOrHashtag}/>
        <DatePicker selectsRange={true}
                    startDate={startDate}
                    endDate={endDate}
                    onChange={(date) => {
                      setSearchDate(date)
                    }}
                    showMonthDropdown
                    showYearDropdown
                    withPortal
                    customInput={<SearchElement/>}/>
        <InputGroup.Text className="search-action" onClick={changeImageSize}><BsImage
          style={{fontSize: currentImageSize + 'em'}}/></InputGroup.Text>
        <InputGroup.Text className="search-action" onClick={search}><BsSearch/></InputGroup.Text>
      </InputGroup>
      <SearchFilter authors={authors} setAuthors={setAuthors} hashtags={hashtags} setHashtags={setHashtags}/>
      {((foundAuthors.isSuccess && foundAuthors.data.length > 0) || (foundHashtags.isSuccess && foundHashtags.data.length > 0)) &&
        <div className="position-absolute w-100 search-content">
          {foundAuthors.isSuccess && foundAuthors.data.map((result) =>
            <div className="search-content-result p-2" key={result.id}
                 onClick={() => {
                   setAuthors([...authors, result])
                   clearSearchField()
                 }}>{result.value}</div>
          )}
          {foundHashtags.isSuccess && foundHashtags.data.map((result) =>
            <div className="search-content-result p-2" key={result.id}
                 onClick={() => {
                   setHashtags([...hashtags, result])
                   clearSearchField()
                 }}>{result.value}</div>
          )}
        </div>}
    </div>
  )
}

export default Search
